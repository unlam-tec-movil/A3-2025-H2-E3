package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews

data class ProfessionalProfileUiState(
    val professionals: Professionals? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class ReviewUiState(
    val reviews: List<Reviews> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
