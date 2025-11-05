package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.NewsApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto.CreateNewsRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto.NewsDto
import ar.edu.unlam.mobile.scaffolding.domain.model.News
import ar.edu.unlam.mobile.scaffolding.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl
    @Inject
    constructor(
        private val newsApi: NewsApi,
    ) : NewsRepository {
        override suspend fun getNews(): Result<List<News>> =
            try {
                val response = newsApi.getNews()
                if (response.isSuccessful) {
                    val newsDtos = response.body() ?: emptyList()
                    val news = newsDtos.map { it.toDomain() }
                    Result.success(news)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun createNews(news: News): Result<News> =
            try {
                val request =
                    CreateNewsRequest(
                        message = news.message,
                        name = news.name,
                        profession = news.profession,
                        userId = news.userId,
                        imgUrl = news.imgUrl,
                        userImgUrl = news.userImgUrl,
                        isLiked = news.isLiked,
                        likes = news.likes,
                    )
                val response = newsApi.createNews(request)
                if (response.isSuccessful) {
                    val createdNews = response.body()?.toDomain() ?: news
                    Result.success(createdNews)
                } else {
                    Result.failure(Exception("Error creando noticia: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
    }

private fun NewsDto.toDomain(): News =
    News(
        id = this.id,
        message = this.message,
        isLiked = this.isLiked,
        likes = this.likes,
        imgUrl = this.imgUrl,
        userImgUrl = this.userImgUrl,
        name = this.name,
        userId = this.userId,
        profession = this.profession,
        createdAt = this.createdAt?.seconds ?: 0L,
    )
