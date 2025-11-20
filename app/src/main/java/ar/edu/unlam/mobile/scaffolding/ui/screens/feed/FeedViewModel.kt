package ar.edu.unlam.mobile.scaffolding.ui.screens.feed

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.services.LocationProvider
import ar.edu.unlam.mobile.scaffolding.data.services.VibratorManager
import ar.edu.unlam.mobile.scaffolding.domain.repository.ProfessionalsRepository
import ar.edu.unlam.mobile.scaffolding.domain.usecase.newUseCase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
    @Inject
    constructor(
        private val getNewsUseCase: GetNewsUseCase,
        private val professionalsRepository: ProfessionalsRepository,
        private val locationProvider: LocationProvider,
        private val vibratorManager: VibratorManager,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(NewsUiState())
        val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

        init {
            loadNews()
            startLocationUpdates()
        }

        private fun startLocationUpdates() {
            viewModelScope.launch {
                locationProvider.getCurrentLocation()
                    .catch { e ->
                        _uiState.update { it.copy(error = e.message) }
                    }
                    .collect { location ->
                        professionalsRepository.getProfessionals().onSuccess {
                            it.forEach { professional ->
                                if (professional.location.size >= 2) {
                                    val lonStr = professional.location[0]
                                    val latStr = professional.location[1]

                                    val lon = lonStr.toDoubleOrNull()
                                    val lat = latStr.toDoubleOrNull()

                                    if (lon != null && lat != null) {
                                        val distance = FloatArray(1)
                                        Location.distanceBetween(
                                            location.latitude,
                                            location.longitude,
                                            lat, // endLatitude
                                            lon, // endLongitude
                                            distance,
                                        )
                                        if (distance[0] < 500) { // 500 meters threshold
                                            vibratorManager.vibrate()
                                        }
                                    }
                                }
                            }
                        }
                    }
            }
        }

        fun loadNews() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getNewsUseCase().fold(
                    onSuccess = { news ->
                        _uiState.update {
                            it.copy(
                                news = news,
                                isLoading = false,
                                error = null,
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "Error desconocido",
                            )
                        }
                    },
                )
            }
        }

        fun refreshNews() {
            loadNews()
        }
    }
