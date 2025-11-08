package ar.edu.unlam.mobile.scaffolding.domain.usecase.reviewsUseCase

import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews
import ar.edu.unlam.mobile.scaffolding.domain.repository.ReviewsRepository
import javax.inject.Inject

class CreateReviewsUseCase
    @Inject
    constructor(
        private val repository: ReviewsRepository,
    ) {
        suspend operator fun invoke(reviews: Reviews): Result<Reviews> = repository.createReviews(reviews)
    }
