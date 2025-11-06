package ar.edu.unlam.mobile.scaffolding.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val getProfessionalsByIdUseCase: GetProfessionalsByIdUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfileUiState())
        val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

        init {
            loadProfessional()
        }

        fun loadProfessional() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getProfessionalsByIdUseCase("7hVfs5ykM3k22wvq5O5d").fold(
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
            loadProfessional()
        }
    }
