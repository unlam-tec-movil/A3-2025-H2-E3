package ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto

import com.google.gson.annotations.SerializedName

data class ReviewsDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("updatedAt") val updatedAt: FirestoreTimestamp,
    @SerializedName("createdAt") val createdAt: FirestoreTimestamp,
    @SerializedName("professionalId") val professionalId: String,
    @SerializedName("stars") val stars: Double,
    @SerializedName("userImageUrl") val userImageUrl: String,
    @SerializedName("userName") val userName: String,
)

data class FirestoreTimestamp(
    @SerializedName("_seconds") val seconds: Long,
    @SerializedName("_nanoseconds") val nanoseconds: Long,
)
