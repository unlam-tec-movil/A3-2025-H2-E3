package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.newUseCase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val getNewsUseCase: GetNewsUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(NewsUiState())
        val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

        init {
            loadNews()
        }

        fun loadNews() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getNewsUseCase().fold(
                    onSuccess = { news ->
                        _uiState.update {
                            it.copy(
                                news = news,
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

        fun refreshNews() {
            loadNews()
        }
    }
