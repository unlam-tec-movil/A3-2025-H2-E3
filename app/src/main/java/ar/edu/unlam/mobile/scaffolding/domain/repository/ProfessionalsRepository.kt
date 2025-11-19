package ar.edu.unlam.mobile.scaffolding.domain.repository

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals

interface ProfessionalsRepository {
    suspend fun getProfessionals(): Result<List<Professionals>>

    suspend fun getProfessionalById(id: String): Result<Professionals>

    suspend fun createProfessionals(professionals: Professionals): Result<Professionals>

    suspend fun updateProfessional(
        id: String,
        professionals: Professionals,
    ): Result<Professionals>

    suspend fun getProfessionalLocations(): Result<List<String>>
}
