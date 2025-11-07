package ar.edu.unlam.mobile.scaffolding.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.common.MapboxOptions.accessToken
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapsResourceOptions
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.style.style

@SuppressLint("RestrictedApi")
@Composable
fun MapboxScreen(modifier: Modifier = Modifier) {
    // 1. Define el estado de la cámara (dónde centrar y qué zoom usar)
    val mapViewportState =
        rememberMapViewportState {
            setCameraOptions {
                // Centrar cerca de Buenos Aires (ejemplo)
                center(Point.fromLngLat(-58.3816, -34.6037))
                zoom(10.0) // Nivel de zoom intermedio
            }
        }

    // 2. Usa el MapboxMap Composable
    MapboxMap(
        modifier = modifier.fillMaxSize(),
        mapViewportState = mapViewportState,
    )
}
