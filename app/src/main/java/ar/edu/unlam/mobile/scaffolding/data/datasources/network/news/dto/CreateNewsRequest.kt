package ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto

import com.google.gson.annotations.SerializedName

data class CreateNewsRequest(
    @SerializedName("message") val message: String,
    @SerializedName("name") val name: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("imgUrl") val imgUrl: String? = null,
    @SerializedName("isLiked") val isLiked: Boolean = false,
    @SerializedName("likes") val likes: Int = 0,
)
