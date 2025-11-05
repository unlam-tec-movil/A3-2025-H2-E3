package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import ar.edu.unlam.mobile.scaffolding.domain.model.News

data class NewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
