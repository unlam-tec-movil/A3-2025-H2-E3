package ar.edu.unlam.mobile.scaffolding.ui.screens.search

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

data class SearchUiState(
    val professionals: List<Professionals> = emptyList(),
    val filteredProfessionals: List<Professionals> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
)
