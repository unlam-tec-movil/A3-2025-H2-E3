package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.ReviewsApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.CreateReviewsRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.reviews.dto.ReviewsDto
import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews
import ar.edu.unlam.mobile.scaffolding.domain.repository.ReviewsRepository
import javax.inject.Inject

class ReviewsRepositoryImpl
    @Inject
    constructor(
        private val reviewsApi: ReviewsApi,
    ) : ReviewsRepository {
        override suspend fun getReviewsByIdProfessional(id: String): Result<List<Reviews>> =
            try {
                val response = reviewsApi.getReviewsByIdProfessional(id)
                if (response.isSuccessful) {
                    val reviewsDtos = response.body() ?: emptyList()
                    val reviews = reviewsDtos.map { it.toDomain() }
                    Result.success(reviews)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun createReviews(reviews: Reviews): Result<Reviews> =
            try {
                val request =
                    CreateReviewsRequest(
                        professionalId = reviews.professionalId,
                        stars = reviews.stars, // ✅ CORREGIDO: No convertir a Double
                        userImageUrl = reviews.userImageUrl,
                        userName = reviews.userName,
                        message = reviews.message, // ✅ AGREGADO
                    )
                val response = reviewsApi.createReviews(request)
                if (response.isSuccessful) {
                    val reviewsDto = response.body()
                    if (reviewsDto != null) {
                        val createdReview = reviewsDto.toDomain()
                        Result.success(createdReview)
                    } else {
                        Result.failure(Exception("Failed to create review"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
    }

// ✅ Extension para mapear DTO a Domain
private fun ReviewsDto.toDomain(): Reviews =
    Reviews(
        id = this.id ?: "",
        updatedAt = this.updatedAt,
        createdAt = this.createdAt,
        professionalId = this.professionalId,
        stars = this.stars,
        userImageUrl = this.userImageUrl,
        userName = this.userName,
        message = this.message,
    )

// ✅ Extension para mapear Domain a DTO (si la necesitas)
fun Reviews.toDto(): ReviewsDto =
    ReviewsDto(
        id = this.id,
        updatedAt = this.updatedAt,
        createdAt = this.createdAt,
        professionalId = this.professionalId,
        stars = this.stars,
        userImageUrl = this.userImageUrl,
        userName = this.userName,
        message = this.message,
    )
