package ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto

import com.google.gson.annotations.SerializedName

data class ProfessionalsDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("createdAt") val createdAt: FirestoreTimestamp,
    @SerializedName("aboutText") val aboutText: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("isProfileHV") val isProfileHV: Boolean,
    @SerializedName("updatedAt") val updatedAt: FirestoreTimestamp,
    @SerializedName("isProfessional") val isProfessional: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("location") val location: List<String>,
    @SerializedName("keyInfo") val keyInfo: Map<String, String>,
    @SerializedName("services") val services: List<String>,
)

data class FirestoreTimestamp(
    @SerializedName("_seconds") val seconds: Long,
    @SerializedName("_nanoseconds") val nanoseconds: Long,
)
