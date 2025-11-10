package ar.edu.unlam.mobile.scaffolding.ui.screens.editUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel
    @Inject
    constructor(
        private val getProfessionalsByIdUseCase: GetProfessionalsByIdUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(EditUserUiState())
        val uiState: StateFlow<EditUserUiState> = _uiState.asStateFlow()

        init {
            loadProfessional()
        }

        fun loadProfessional() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                try {
                    println("DEBUG: Cargando profesional con ID: ${UserId.ID}")

                    getProfessionalsByIdUseCase(UserId.ID).fold(
                        onSuccess = { professionals ->
                            println("DEBUG: Datos recibidos - $professionals")
                            println("DEBUG: Nombre: ${professionals?.name}")
                            println("DEBUG: Profesión: ${professionals?.profession}")
                            println("DEBUG: About: ${professionals?.aboutText}")
                            println("DEBUG: KeyInfo: ${professionals?.keyInfo}")
                            println("DEBUG: Services: ${professionals?.services}")

                            _uiState.update {
                                it.copy(
                                    professionals = professionals,
                                    isLoading = false,
                                    error = null,
                                )
                            }
                        },
                        onFailure = { error ->
                            println("DEBUG: Error cargando profesional: $error")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = error.message ?: "Error desconocido al cargar los datos",
                                )
                            }
                        },
                    )
                } catch (e: Exception) {
                    println("DEBUG: Excepción inesperada: $e")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error inesperado: ${e.message}",
                        )
                    }
                }
            }
        }

        fun refreshProfessional() {
            loadProfessional()
        }

        fun saveChanges(
            fullName: String,
            profession: String,
            aboutMe: String,
            location: String,
            experience: String,
            licenseNumber: String,
            services: List<String>,
        ) {
            viewModelScope.launch {
                _uiState.update { it.copy(isSaving = true, error = null) }

                try {
                    // TODO: Implementar la lógica para guardar los cambios
                    // Por ahora solo simulamos el guardado
                    println("DEBUG: Guardando cambios...")
                    println("DEBUG: Nombre: $fullName")
                    println("DEBUG: Profesión: $profession")
                    println("DEBUG: About: $aboutMe")
                    println("DEBUG: Location: $location")
                    println("DEBUG: Experience: $experience")
                    println("DEBUG: License: $licenseNumber")
                    println("DEBUG: Services: $services")

                    // Simular tiempo de guardado
                    kotlinx.coroutines.delay(1000)

                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            saveSuccess = true,
                            // Actualizar el profesional localmente
                            professionals =
                                it.professionals?.copy(
                                    name = fullName,
                                    profession = profession,
                                    aboutText = aboutMe,
                                    keyInfo =
                                        mapOf(
                                            "Zona de Cobertura" to location,
                                            "años de experiencia" to experience,
                                            "Matricula" to licenseNumber,
                                        ),
                                    services = services,
                                ),
                        )
                    }

                    // Resetear el éxito después de un tiempo
                    viewModelScope.launch {
                        kotlinx.coroutines.delay(3000)
                        _uiState.update { it.copy(saveSuccess = false) }
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            error = "Error al guardar cambios: ${e.message}",
                        )
                    }
                }
            }
        }

        fun clearError() {
            _uiState.update { it.copy(error = null) }
        }

        fun clearSaveSuccess() {
            _uiState.update { it.copy(saveSuccess = false) }
        }
    }
