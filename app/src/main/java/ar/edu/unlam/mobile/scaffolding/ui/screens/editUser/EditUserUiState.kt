package ar.edu.unlam.mobile.scaffolding.ui.screens.editUser

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

data class EditUserUiState(
    val professionals: Professionals? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
)
