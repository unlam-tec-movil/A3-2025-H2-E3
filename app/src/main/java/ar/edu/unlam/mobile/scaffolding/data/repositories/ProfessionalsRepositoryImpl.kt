package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.ProfessionalsApi
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.professionals.dto.ProfessionalsDto
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import javax.inject.Inject

class ProfessionalsRepositoryImpl
    @Inject
    constructor(
        private val professionalsApi: ProfessionalsApi,
    ) : ProfessionalsRepository {
        override suspend fun getProfessionals(): Result<List<Professionals>> =
            try {
                val response = professionalsApi.getProfessionals()
                if (response.isSuccessful) {
                    val professionalsDtos = response.body() ?: emptyList()
                    val professionals = professionalsDtos.map { it.toDomain() }
                    Result.success(professionals)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun getProfessionalById(id: String): Result<Professionals> =
            try {
                val response = professionalsApi.getProfessionalById(id)
                if (response.isSuccessful) {
                    val professionalDto = response.body()
                    if (professionalDto != null) {
                        val professional = professionalDto.toDomain()
                        Result.success(professional)
                    } else {
                        Result.failure(Exception("Professional not found"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }

        override suspend fun createProfessionals(professionals: Professionals): Result<Professionals> =
            Result.failure(Exception("Método no implementado"))
    }

private fun ProfessionalsDto.toDomain(): Professionals =
    Professionals(
        id = this.id ?: "",
        createdAt = this.createdAt,
        aboutText = this.aboutText,
        imgUrl = this.imgUrl,
        updatedAt = this.updatedAt,
        name = this.name,
        profession = this.profession,
        rating = this.rating,
        location = this.location,
        keyInfo = this.keyInfo,
        services = this.services,
        isProfileHV = this.isProfileHV,
    )
