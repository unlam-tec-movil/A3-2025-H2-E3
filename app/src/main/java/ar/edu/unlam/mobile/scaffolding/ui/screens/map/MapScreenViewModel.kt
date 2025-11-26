package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.usecase.userUseCase.GetProfessionalsUseCase
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.SearchUiState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
    @Inject
    constructor(
        private val getProfessionalsUseCase: GetProfessionalsUseCase,
        private val repository: MapsRepository,
        private val app: Application,
    ) : ViewModel() {
        private val fusedLocation = LocationServices.getFusedLocationProviderClient(app)

        private val _ubicacionActual =
            MutableStateFlow(
                LatLng(-34.603722, -58.381592), // fallback (Obelisco)
            )
        val ubicacionActual = _ubicacionActual.asStateFlow()
        private val _routePoints = MutableStateFlow<List<LatLng>>(emptyList())
        val routePoints = _routePoints.asStateFlow()

        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        val apiKey = "AIzaSyAMsvwQd-AYp436UM5XM"

        // reemplazar guion de arriba por: nU8wQQpbOjiemt0
        fun actualizarUbicacion() {
            try {
                fusedLocation.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        _ubicacionActual.value = LatLng(loc.latitude, loc.longitude)
                    }
                }
            } catch (_: SecurityException) {
            }
        }

        init {
            loadProfessional()
        }

        fun loadRoute(
            origin: LatLng,
            destination: LatLng,
            apiKey: String,
        ) {
            viewModelScope.launch {
                val result = repository.getRoute(origin, destination, apiKey)
                _routePoints.value = result
            }
        }

        fun loadProfessional() {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }

                getProfessionalsUseCase().fold(
                    onSuccess = { professionals ->
                        _uiState.update {
                            it.copy(
                                professionals = professionals,
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

        fun refreshProfessional() {
            loadProfessional()
        }
    }
