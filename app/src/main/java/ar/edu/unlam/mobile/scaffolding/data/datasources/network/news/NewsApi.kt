package ar.edu.unlam.mobile.scaffolding.data.datasources.network.news

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto.CreateNewsRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.news.dto.NewsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NewsApi {
    @GET("news")
    suspend fun getNews(): Response<List<NewsDto>>

    @POST("news")
    suspend fun createNews(
        @Body request: CreateNewsRequest,
    ): Response<NewsDto>
}
