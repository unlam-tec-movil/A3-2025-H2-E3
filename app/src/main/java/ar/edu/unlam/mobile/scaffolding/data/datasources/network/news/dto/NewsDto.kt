package ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto

import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("id") val id: String,
    @SerializedName("message") val message: String,
    @SerializedName("isLiked") val isLiked: Boolean,
    @SerializedName("likes") val likes: Int,
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("userImgUrl") val userImgUrl: String?,
    @SerializedName("name") val name: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("createdAt") val createdAt: FirestoreTimestamp?,
    @SerializedName("updatedAt") val updatedAt: FirestoreTimestamp?,
)

data class FirestoreTimestamp(
    @SerializedName("_seconds") val seconds: Long,
    @SerializedName("_nanoseconds") val nanoseconds: Long,
)
