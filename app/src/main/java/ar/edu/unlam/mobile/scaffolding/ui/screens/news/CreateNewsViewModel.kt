package ar.edu.unlam.mobile.scaffolding.ui.screens.createnews

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repositories.StorageRepository
import ar.edu.unlam.mobile.scaffolding.domain.model.News
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.domain.usecase.newUseCase.CreateNewsUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.CreateNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNewsViewModel
    @Inject
    constructor(
        private val createNewsUseCase: CreateNewsUseCase,
        private val storageRepository: StorageRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CreateNewsUiState())
        val uiState: StateFlow<CreateNewsUiState> = _uiState.asStateFlow()

        private var currentProfessional: Professionals? = null

        fun setProfessionalData(professional: Professionals?) {
            currentProfessional = professional
        }

        fun updateNewsContent(content: String) {
            _uiState.update { it.copy(newsContent = content) }
        }

        fun updateSelectedImage(uri: Uri?) {
            _uiState.update { it.copy(selectedImageUri = uri) }
        }

        fun clearImage() {
            _uiState.update { it.copy(selectedImageUri = null) }
        }

        fun publishNews() {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        isPublishing = true,
                        error = null,
                        publishSuccess = false,
                    )
                }

                try {
                    // Validaciones
                    if (_uiState.value.newsContent.isBlank()) {
                        _uiState.update {
                            it.copy(
                                isPublishing = false,
                                error = "Por favor, escribe el contenido de la noticia",
                            )
                        }
                        return@launch
                    }

                    // 1. Subir imagen a Firebase Storage si existe
                    val imageUrl =
                        _uiState.value.selectedImageUri?.let { uri ->
                            try {
                                storageRepository.uploadNewsImage(UserId.ID, uri)
                            } catch (e: Exception) {
                                _uiState.update {
                                    it.copy(
                                        isPublishing = false,
                                        error = "Error al subir imagen: ${e.message}",
                                    )
                                }
                                return@launch
                            }
                        }

                    // 2. Crear objeto News con el formato que espera tu backend
                    val news =
                        News(
                            id = "", // El backend generará el ID
                            message = _uiState.value.newsContent,
                            isLiked = false,
                            likes = 0,
                            imgUrl =
                                imageUrl
                                    ?: "https://biblus.accasoftware.com/es/wp-content" +
                                    "/uploads/sites/3/2023/02/intervencion-de-reparacion.jpg",
                            userImgUrl = currentProfessional?.imgUrl,
                            name = currentProfessional?.name ?: "Usuario",
                            userId = UserId.ID,
                            profession = currentProfessional?.profession ?: "Profesional",
                            createdAt = System.currentTimeMillis() / 1000,
                        )

                    println("DEBUG: Enviando noticia a API: $news")

                    // 3. Llamar al caso de uso para crear la noticia
                    val result = createNewsUseCase(news)

                    result.fold(
                        onSuccess = { createdNews ->
                            println("DEBUG: Noticia creada exitosamente: ${createdNews.id}")
                            _uiState.update {
                                it.copy(
                                    isPublishing = false,
                                    publishSuccess = true,
                                    error = null,
                                )
                            }
                        },
                        onFailure = { error ->
                            println("DEBUG: Error al crear noticia: ${error.message}")
                            _uiState.update {
                                it.copy(
                                    isPublishing = false,
                                    error = "Error al publicar noticia: ${error.message}",
                                    publishSuccess = false,
                                )
                            }
                        },
                    )
                } catch (e: Exception) {
                    println("DEBUG: Excepción inesperada: ${e.message}")
                    _uiState.update {
                        it.copy(
                            isPublishing = false,
                            error = "Error inesperado: ${e.message}",
                            publishSuccess = false,
                        )
                    }
                }
            }
        }

        fun clearPublishStatus() {
            _uiState.update {
                it.copy(
                    publishSuccess = false,
                    error = null,
                )
            }
        }

        fun clearError() {
            _uiState.update { it.copy(error = null) }
        }

        fun resetForm() {
            _uiState.update {
                CreateNewsUiState()
            }
        }
    }
