package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.model.News

interface NewsRepository {
    suspend fun getNews(): Result<List<News>>

    suspend fun createNews(news: News): Result<News>
}
