package ar.edu.unlam.mobile.scaffolding.ui.screens.review

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

data class ReviewUiState(
    val profileOwner: Professionals? = null,
    val currentUser: Professionals? = null,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val reviewSubmitted: Boolean = false,
)
