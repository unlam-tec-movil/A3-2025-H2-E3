package ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto

import com.google.gson.annotations.SerializedName

data class CreateProfessionalsRequest(
    @SerializedName("id") val id: String? = null,
    @SerializedName("aboutText") val aboutText: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("location") val location: List<Double>,
    @SerializedName("keyInfo") val keyInfo: Map<String, String>,
    @SerializedName("services") val services: List<String>,
    @SerializedName("isProfileHV") val isProfileHV: Boolean,
)
