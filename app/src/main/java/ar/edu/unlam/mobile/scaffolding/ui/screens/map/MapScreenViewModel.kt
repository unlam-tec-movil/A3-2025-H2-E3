package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
    @Inject
    constructor(
        private val getProfessionalsUseCase: GetProfessionalsUseCase,
    ) : ViewModel()
