package ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto.CreateProfessionalsRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto.ProfessionalsDto
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto.UpdateProfessionalsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfessionalsApi {
    @GET("professionals")
    suspend fun getProfessionals(): Response<List<ProfessionalsDto>>

    @GET("professionals/{id}")
    suspend fun getProfessionalById(
        @Path("id") id: String,
    ): Response<ProfessionalsDto>

    @POST("professionals")
    suspend fun createProfessional(
        @Body request: CreateProfessionalsRequest,
    ): Response<ProfessionalsDto>

    @PUT("professionals/{id}")
    suspend fun updateProfessional(
        @Path("id") id: String,
        @Body request: UpdateProfessionalsRequest,
    ): Response<ProfessionalsDto>
}
