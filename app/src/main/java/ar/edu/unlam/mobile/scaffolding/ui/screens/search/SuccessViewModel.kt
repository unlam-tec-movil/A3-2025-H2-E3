package ar.edu.unlam.mobile.scaffolding.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsUseCase
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
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

        // Estado para la categoría seleccionada
        private val _selectedCategory = MutableStateFlow("Todos")
        val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

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
                                filteredProfessionals = applyFilters(professionals, _selectedCategory.value, ""),
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

        // Nuevos métodos para filtros
        fun setCategoryFilter(category: String) {
            _selectedCategory.value = category
            applyFilters()
        }

        fun setSearchQuery(query: String) {
            _uiState.update { it.copy(searchQuery = query) }
            applyFilters()
        }

        private fun applyFilters() {
            val currentProfessionals = _uiState.value.professionals
            val filtered = applyFilters(currentProfessionals, _selectedCategory.value, _uiState.value.searchQuery)
            _uiState.update { it.copy(filteredProfessionals = filtered) }
        }

        private fun applyFilters(
            professionals: List<Professionals>,
            category: String,
            query: String,
        ): List<Professionals> =
            professionals.filter { professional ->
                val matchesCategory =
                    when (category) {
                        "Todos" -> true
                        else ->
                            professional.profession.contains(category, ignoreCase = true) ||
                                professional.services.any { service ->
                                    service.contains(category, ignoreCase = true)
                                }
                    }

                val matchesQuery =
                    if (query.trim().isEmpty()) {
                        true
                    } else {
                        professional.profession.contains(query, ignoreCase = true) ||
                            professional.name.contains(query, ignoreCase = true) ||
                            professional.services.any { service ->
                                service.contains(query, ignoreCase = true)
                            }
                    }

                matchesCategory && matchesQuery && professional.id != UserId.ID
            }
    }
