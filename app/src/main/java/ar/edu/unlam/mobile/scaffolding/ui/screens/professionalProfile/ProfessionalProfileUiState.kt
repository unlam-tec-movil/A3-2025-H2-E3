package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

data class ProfessionalProfileUiState(
    val professionals: Professionals? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
