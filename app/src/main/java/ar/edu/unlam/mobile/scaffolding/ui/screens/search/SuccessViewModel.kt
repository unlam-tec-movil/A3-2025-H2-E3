package ar.edu.unlam.mobile.scaffolding.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SuccessViewModel
    @Inject
    constructor(
        private val getProfessionalsUseCase: GetProfessionalsUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        init {
            loadProfessional()
        }

        fun loadProfessional() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getProfessionalsUseCase().fold(
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
