package ar.edu.unlam.mobile.scaffolding.domain.usecase.newUseCase

import ar.edu.unlam.mobile.scaffolding.domain.model.News
import ar.edu.unlam.mobile.scaffolding.domain.repository.NewsRepository
import javax.inject.Inject

class CreateNewsUseCase
    @Inject
    constructor(
        private val repository: NewsRepository,
    ) {
        suspend operator fun invoke(news: News): Result<News> = repository.createNews(news)
    }
