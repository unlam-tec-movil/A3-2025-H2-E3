package ar.edu.unlam.mobile.scaffolding.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.reviewsUseCase.GetReviewsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsByIdUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.ReviewUiState
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
        private val getReviewsUseCase: GetReviewsUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ProfileUiState())
        val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

        private val _uiStateReview = MutableStateFlow(ReviewUiState())
        val uiStateReview: StateFlow<ReviewUiState> = _uiStateReview.asStateFlow()

        init {
            loadProfessional()
            loadReviews(UserId.ID)
        }

        fun loadProfessional() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getProfessionalsByIdUseCase(UserId.ID).fold(
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

        fun loadReviews(id: String) {
            viewModelScope.launch {
                _uiStateReview.update { it.copy(isLoading = true, error = null) }

                getReviewsUseCase(id).fold(
                    onSuccess = { reviews ->
                        _uiStateReview.update {
                            it.copy(
                                reviews = reviews,
                                isLoading = false,
                                error = null,
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiStateReview.update {
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
