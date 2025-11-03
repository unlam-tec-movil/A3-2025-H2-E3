package ar.edu.unlam.mobile.scaffolding.domain.model

data class News(
    val id: String,
    val message: String,
    val isLiked: Boolean,
    val likes: Int,
    val imgUrl: String?,
    val name: String,
    val userId: String,
    val profession: String,
    val createdAt: Long,
)
