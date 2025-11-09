package ar.edu.unlam.mobile.scaffolding.ui.screens.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewViewModel
    @Inject
    constructor(
        private val getProfessionalsByIdUseCase: GetProfessionalsByIdUseCase,
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

        fun refreshProfessionals() {
            loadBothProfessionals(professionalId, UserId.ID)
        }
    }
