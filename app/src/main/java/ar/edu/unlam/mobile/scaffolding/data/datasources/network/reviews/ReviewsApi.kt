package ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.CreateReviewsRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.ReviewsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewsApi {
    @GET("reviews/professional/{id}")
    suspend fun getReviewsByIdProfessional(
        @Path("id") id: String,
    ): Response<List<ReviewsDto>>

    @POST("reviews")
    suspend fun createReviews(
        @Body request: CreateReviewsRequest,
    ): Response<ReviewsDto>
}
