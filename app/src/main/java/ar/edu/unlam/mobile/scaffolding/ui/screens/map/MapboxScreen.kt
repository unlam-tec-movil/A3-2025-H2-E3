package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import android.Manifest
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.math.abs

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

    // Unica fuente de verdad para el estado de la camara
    val cameraState = rememberCameraPositionState()

    // Efecto para posicionar la camara cuando hay un destino
    LaunchedEffect(profesionalId, profesionales) {
        if (profesionalId != null) {
            val profesional = profesionales.firstOrNull { it.id == profesionalId }

            if (profesional?.location?.size == 2) {
                val lon = profesional.location[0].toDoubleOrNull()
                val lat = profesional.location[1].toDoubleOrNull()
                if (lon != null && lat != null) {
                    val professionalLocation = LatLng(lat, lon)
                    destino.value = professionalLocation
                    // Mueve la camara una sola vez al destino
                    cameraState.move(
                        update =
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.fromLatLngZoom(professionalLocation, 14f),
                            ),
                    )
                }
            }
        }
    }

    // Efecto para animar a la ubicacion del usuario, solo en el mapa general
    LaunchedEffect(origin) {
        if (profesionalId == null && origin.latitude != 0.0 && origin.longitude != 0.0) {
            cameraState.animate(
                update = CameraUpdateFactory.newLatLngZoom(origin, 12f),
                durationMs = 1200,
            )
        }
    }

    if (profesionalId == null) {
        MapaCompleto(
            modifier = modifier,
            cameraState = cameraState,
            profesionales = profesionales,
        )
    } else {
        destino.value?.let { dest ->
            MapaConRuta(
                modifier = modifier,
                destino = dest,
                cameraState = cameraState, // Pasa el estado unico de la camara
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapaCompleto(
    modifier: Modifier = Modifier,
    cameraState: CameraPositionState,
    profesionales: List<Professionals>,
) {
    val permisoUbicacion = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        permisoUbicacion.launchPermissionRequest()
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraState,
        properties = MapProperties(isMyLocationEnabled = permisoUbicacion.status.isGranted),
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
    ) {
        profesionales.forEach { profesional ->
            val lon = profesional.location.getOrNull(0)?.toDoubleOrNull()
            val lat = profesional.location.getOrNull(1)?.toDoubleOrNull()

            if (lon != null && lat != null) {
                val professionalLatLng = LatLng(lat, lon)
                var bitmapDescriptor by remember(profesional.id) { mutableStateOf<BitmapDescriptor?>(null) }
                val context = LocalContext.current

                LaunchedEffect(profesional.imgUrl) {
                    val loader = ImageLoader(context)
                    val request =
                        ImageRequest
                            .Builder(context)
                            .data(profesional.imgUrl)
                            .allowHardware(false)
                            .size(128, 128)
                            .transformations(CircleCropTransformation())
                            .build()

                    val result = loader.execute(request)
                    if (result is SuccessResult) {
                        val bitmap = (result.drawable as BitmapDrawable).bitmap
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap)
                    }
                }

                Marker(
                    state = MarkerState(position = professionalLatLng),
                    title = profesional.name,
                    snippet = profesional.profession,
                    icon =
                        bitmapDescriptor ?: BitmapDescriptorFactory.defaultMarker(
                            (abs(profesional.id.hashCode()) % 360).toFloat(),
                        ),
                )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapaConRuta(
    modifier: Modifier = Modifier,
    destino: LatLng,
    cameraState: CameraPositionState, // Recibe el estado de la camara
    viewModel: MapScreenViewModel = hiltViewModel(),
) {
    val apiKey = viewModel.apiKey
    val ubicacionActual by viewModel.ubicacionActual.collectAsState()
    val route by viewModel.routePoints.collectAsState()

    val permiso = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        permiso.launchPermissionRequest()
    }

    LaunchedEffect(permiso.status) {
        if (permiso.status.isGranted) {
            viewModel.actualizarUbicacion()
        }
    }

    LaunchedEffect(ubicacionActual, destino) {
        viewModel.loadRoute(ubicacionActual, destino, apiKey)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraState, // Usa el estado de la camara recibido
        properties = MapProperties(isMyLocationEnabled = permiso.status.isGranted),
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
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
