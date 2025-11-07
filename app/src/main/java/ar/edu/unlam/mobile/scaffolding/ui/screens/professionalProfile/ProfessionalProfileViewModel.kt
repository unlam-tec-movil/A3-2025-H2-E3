package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfessionalProfileViewModel
    @Inject
    constructor(
        private val getProfessionalsByIdUseCase: GetProfessionalsByIdUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfessionalProfileUiState())
        val uiState: StateFlow<ProfessionalProfileUiState> = _uiState.asStateFlow()

        private val professionalId: String = savedStateHandle["id"] ?: ""

        init {
            if (professionalId.isNotEmpty()) loadProfessional(professionalId)
        }

        fun loadProfessional(id: String) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getProfessionalsByIdUseCase(id).fold(
                    onSuccess = { professionals ->
                        _uiState.update {
                            it.copy(
                                professionals = professionals,
                                isLoading = false,
                                error = null,
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Error desconocido",
                            )
                        }
                    },
                )
            }
        }

        fun refreshProfessional() {
            loadProfessional(professionalId)
        }
    }
