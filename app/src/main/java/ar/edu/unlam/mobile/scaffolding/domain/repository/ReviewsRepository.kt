package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews

interface ReviewsRepository {
    suspend fun getReviewsByIdProfessional(id: String): Result<List<Reviews>>

    suspend fun createReviews(reviews: Reviews): Result<Reviews>
}
