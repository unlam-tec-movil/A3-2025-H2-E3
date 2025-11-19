package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import javax.inject.Inject

class GetProfessionalsLocationsUseCase
    @Inject
    constructor(
        private val repository: ProfessionalsRepository,
    )
