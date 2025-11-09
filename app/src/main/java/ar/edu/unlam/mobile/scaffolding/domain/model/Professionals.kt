package ar.edu.unlam.mobile.scaffolding.domain.model

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto.FirestoreTimestamp

data class Professionals(
    val id: String,
    val createdAt: FirestoreTimestamp,
    val aboutText: String,
    val imgUrl: String,
    val updatedAt: FirestoreTimestamp,
    val name: String,
    val profession: String,
    val rating: Double,
    val location: List<Double>,
    val keyInfo: Map<String, String>,
    val services: List<String>,
    val isProfileHV: Boolean,
    val isProfessional: Boolean,
)
