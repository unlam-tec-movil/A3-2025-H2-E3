package ar.edu.unlam.mobile.scaffolding.domain.usecase.reviewsUseCase

import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews
import ar.edu.unlam.mobile.scaffolding.domain.repository.ReviewsRepository
import javax.inject.Inject

class GetReviewsUseCase
    @Inject
    constructor(
        private val repository: ReviewsRepository,
    ) {
        suspend operator fun invoke(id: String): Result<List<Reviews>> = repository.getReviewsByIdProfessional(id)
    }
