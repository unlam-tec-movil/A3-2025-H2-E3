package ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase

import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import javax.inject.Inject

class GetProfessionalsUseCase
    @Inject
    constructor(
        private val repository: ProfessionalsRepository,
    ) {
        suspend operator fun invoke(): Result<List<Professionals>> = repository.getProfessionals()
    }
