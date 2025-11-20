package ar.edu.unlam.mobile.scaffolding.domain.model

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.FirestoreTimestamp

data class Reviews(
    val id: String,
    val updatedAt: FirestoreTimestamp,
    val createdAt: FirestoreTimestamp,
    val professionalId: String,
    val stars: Int,
    val userImageUrl: String,
    val userName: String,
    val message: String,
)
