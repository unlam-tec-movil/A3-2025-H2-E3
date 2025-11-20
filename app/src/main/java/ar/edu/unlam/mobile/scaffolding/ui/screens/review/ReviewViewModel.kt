package ar.edu.unlam.mobile.scaffolding.ui.screens.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.FirestoreTimestamp
import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews
import ar.edu.unlam.mobile.scaffolding.domain.usecase.reviewsUseCase.CreateReviewsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

@HiltViewModel
class ReviewViewModel
    @Inject
    constructor(
        private val getProfessionalsByIdUseCase: GetProfessionalsByIdUseCase,
        private val createReviewsUseCase: CreateReviewsUseCase, // ✅ AGREGADO
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ReviewUiState())
        val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

        private val professionalId: String = savedStateHandle["id"] ?: ""

        init {
            if (professionalId.isNotEmpty()) {
                loadBothProfessionals(professionalId, UserId.ID)
            }
        }

        private fun loadBothProfessionals(
            profileOwnerId: String,
            currentUserId: String,
        ) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                // Cargar el dueño del perfil
                val profileOwnerResult = getProfessionalsByIdUseCase(profileOwnerId)
                // Cargar el usuario actual
                val currentUserResult = getProfessionalsByIdUseCase(currentUserId)

                // Combinar resultados
                if (profileOwnerResult.isSuccess && currentUserResult.isSuccess) {
                    _uiState.update {
                        it.copy(
                            profileOwner = profileOwnerResult.getOrNull(),
                            currentUser = currentUserResult.getOrNull(),
                            isLoading = false,
                            error = null,
                        )
                    }
                } else {
                    val error = profileOwnerResult.exceptionOrNull() ?: currentUserResult.exceptionOrNull()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error?.message ?: "Error desconocido",
                        )
                    }
                }
            }
        }

        // ✅ NUEVA FUNCIÓN PARA CREAR REVIEW
        fun submitReview(
            stars: Int,
            message: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSubmitting = true, error = null) }

                val currentUser = _uiState.value.currentUser
                val profileOwner = _uiState.value.profileOwner

                if (currentUser == null || profileOwner == null) {
                    _uiState.update { it.copy(isSubmitting = false, error = "Información de usuario no disponible") }
                    onError("Información de usuario no disponible")
                    return@launch
                }

                if (stars == 0) {
                    _uiState.update { it.copy(isSubmitting = false, error = "Por favor selecciona una calificación") }
                    onError("Por favor selecciona una calificación")
                    return@launch
                }

                if (message.isBlank()) {
                    _uiState.update { it.copy(isSubmitting = false, error = "Por favor escribe una reseña") }
                    onError("Por favor escribe una reseña")
                    return@launch
                }

                // Crear objeto Review
                val review =
                    Reviews(
                        id = "", // El servidor generará el ID
                        updatedAt = createFirestoreTimestamp(),
                        createdAt = createFirestoreTimestamp(),
                        professionalId = professionalId,
                        stars = stars,
                        userImageUrl = currentUser.imgUrl ?: "",
                        userName = currentUser.name ?: "Usuario Anónimo",
                        message = message,
                    )

                val result = createReviewsUseCase(review)

                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = null,
                            reviewSubmitted = true,
                        )
                    }
                    onSuccess()
                } else {
                    val errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido al crear la reseña"
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = errorMessage,
                        )
                    }
                    onError(errorMessage)
                }
            }
        }

        // ✅ FUNCIÓN PARA CREAR TIMESTAMP (placeholder - el servidor lo sobreescribirá)
        private fun createFirestoreTimestamp(): FirestoreTimestamp {
            val now = Date()
            return FirestoreTimestamp(
                seconds = now.time / 1000,
                nanoseconds = ((now.time % 1000) * 1_000_000).toLong(),
            )
        }

        fun refreshProfessionals() {
            loadBothProfessionals(professionalId, UserId.ID)
        }

        // ✅ FUNCIÓN PARA LIMPIAR ERROR
        fun clearError() {
            _uiState.update { it.copy(error = null) }
        }
    }
