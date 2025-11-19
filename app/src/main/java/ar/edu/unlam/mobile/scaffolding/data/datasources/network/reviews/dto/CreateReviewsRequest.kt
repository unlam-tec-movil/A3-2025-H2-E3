package ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto

import com.google.gson.annotations.SerializedName

data class CreateReviewsRequest(
    @SerializedName("professionalId") val professionalId: String,
    @SerializedName("stars") val stars: Int,
    @SerializedName("userImageUrl") val userImageUrl: String? = null,
    @SerializedName("userName") val userName: String,
    @SerializedName("message") val message: String,
)
