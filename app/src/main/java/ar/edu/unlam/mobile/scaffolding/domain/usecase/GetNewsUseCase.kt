package ar.edu.unlam.mobile.scaffolding.domain.usecase

import ar.edu.unlam.mobile.scaffolding.domain.model.News
import ar.edu.unlam.mobile.scaffolding.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsUseCase
    @Inject
    constructor(
        private val repository: NewsRepository,
    ) {
        suspend operator fun invoke(): Result<List<News>> = repository.getNews()
    }
