package ar.edu.unlam.mobile.scaffolding.ui.screens.profile

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

data class ProfileUiState(
    val professionals: Professionals? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
