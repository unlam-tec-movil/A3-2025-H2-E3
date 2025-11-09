package ar.edu.unlam.mobile.scaffolding.domain.model

data class Reviews(
    val id: String,
    val updatedAt: ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.FirestoreTimestamp,
    val createdAt: ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.FirestoreTimestamp,
    val professionalId: String,
    val stars: Double,
    val userImageUrl: String,
    val userName: String,
    val message: String,
)
