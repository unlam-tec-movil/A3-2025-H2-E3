package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import ar.edu.unlam.mobile.scaffolding.domain.model.News

data class NewsUiState(
    val news: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

fun News.toUiModel(): NewsItemUiModel =
    NewsItemUiModel(
        id = id,
        name = name,
        profession = profession,
        message = message,
        isLiked = isLiked,
        likes = likes,
        imgUrl = imgUrl,
    )

data class NewsItemUiModel(
    val id: String,
    val name: String,
    val profession: String,
    val message: String,
    val isLiked: Boolean,
    val likes: Int,
    val imgUrl: String?,
)
