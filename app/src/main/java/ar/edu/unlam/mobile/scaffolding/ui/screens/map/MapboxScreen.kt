package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapboxScreen(
    modifier: Modifier = Modifier,
    viewModel: MapScreenViewModel = hiltViewModel(),
    profesionalId: String? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    val origin by viewModel.ubicacionActual.collectAsState()

    val profesionales = uiState.professionals

    val permiso = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    LaunchedEffect(Unit) {
        permiso.launchPermissionRequest()
    }

    LaunchedEffect(permiso.status) {
        if (permiso.status.isGranted) {
            viewModel.actualizarUbicacion()
        }
    }

    val destino = remember { mutableStateOf<LatLng?>(null) }

    LaunchedEffect(profesionalId, profesionales) {
        if (profesionalId != null) {
            val profesional = profesionales.firstOrNull { it.id == profesionalId }

            if (profesional?.location?.size == 2) {
                val lon = profesional.location[0].toDouble()
                val lat = profesional.location[1].toDouble()
                destino.value = LatLng(lat, lon)
            }
        }
    }

    // Lista de puntos de profesionales
    val puntos =
        remember(profesionales) {
            profesionales.mapNotNull { p ->
                if (p.location.size == 2) {
                    LatLng(
                        p.location[1].toDouble(), // LAT
                        p.location[0].toDouble(), // LON
                    )
                } else {
                    null
                }
            }
        }

    val cameraState = rememberCameraPositionState()

    // Mueve la cámara al origin cuando llegue ubicación real
    LaunchedEffect(origin) {
        if (origin.latitude != 0.0 && origin.longitude != 0.0) {
            cameraState.animate(
                update = CameraUpdateFactory.newLatLngZoom(origin, 12f),
                durationMs = 1200,
            )
        }
    }

    if (profesionalId == null) {
        Column(modifier = modifier.fillMaxSize()) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Ubicaciones",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            }
            HorizontalDivider()
            MapaCompleto(
                cameraState = cameraState,
                puntos = puntos,
                permisoUbicacion = permiso,
            )
        }
    } else {
        destino.value?.let { dest ->
            MapaConRuta(
                destino = dest,
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapaCompleto(
    cameraState: CameraPositionState,
    puntos: List<LatLng>,
    permisoUbicacion: PermissionState,
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties =
            MapProperties(
                isMyLocationEnabled = permisoUbicacion.status.isGranted,
            ),
    ) {
        puntos.forEach { punto ->
            Marker(
                state = MarkerState(position = punto),
                title = "Profesional",
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapaConRuta(
    destino: LatLng,
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val apiKey = viewModel.apiKey
    val ubicacionActual by viewModel.ubicacionActual.collectAsState()
    val route by viewModel.routePoints.collectAsState()

    val permiso = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        permiso.launchPermissionRequest()
    }

    // Cuando el permiso cambie, obtener la ubicación real
    LaunchedEffect(permiso.status) {
        if (permiso.status.isGranted) {
            viewModel.actualizarUbicacion()
        }
    }

    // Cuando la ubicación real cambie recalcula la ruta
    LaunchedEffect(ubicacionActual, destino) {
        viewModel.loadRoute(ubicacionActual, destino, apiKey)
    }

    val cameraState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(ubicacionActual, 14f)
        }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(isMyLocationEnabled = permiso.status.isGranted),
    ) {
        Marker(
            state = MarkerState(destino),
            title = "Destino",
        )

        if (route.isNotEmpty()) {
            Polyline(points = route, color = Color.Blue, width = 10f)
        }
    }
}
