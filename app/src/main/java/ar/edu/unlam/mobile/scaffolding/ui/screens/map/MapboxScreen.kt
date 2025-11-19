package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.SuccessViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage

@SuppressLint("RestrictedApi")
@Composable
fun MapboxScreen(
    modifier: Modifier = Modifier,
    viewModel: SuccessViewModel = hiltViewModel(),
    profesionalId: String? = null,
) {
    val uiState by viewModel.uiState.collectAsState()

    val pinIcon =
        rememberIconImage(
            key = "pin",
            painter = painterResource(id = android.R.drawable.star_on),
        )

    val ubicacion =
        rememberIconImage(
            key = "marker",
            painter = painterResource(id = android.R.drawable.radiobutton_on_background),
        )

    val puntoA = Point.fromLngLat(-58.4355, -34.6065)

    var textoMapa: String = "Profesionales Cercanos"
    val listaLocaciones = uiState.professionals
    var listafiltrada: List<Professionals>

    if (profesionalId != null) {
        listafiltrada = listaLocaciones.filter { it.id == profesionalId }

        textoMapa = "Ubicacion del Profesional"
    } else {
        listafiltrada = listaLocaciones
    }

    val mapViewportState =
        rememberMapViewportState {
            setCameraOptions {
                center(puntoA)
                zoom(10.0)
            }
        }

    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
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
                text = textoMapa,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }
        HorizontalDivider()

        MapboxMap(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            mapViewportState = mapViewportState,
        ) {
            PointAnnotation(point = puntoA) { iconImage = ubicacion } // Point 1
            listafiltrada.forEach { professionals ->
                val latStr = professionals.location.getOrNull(1)
                val lonStr = professionals.location.getOrNull(0)

                var lat: Double? = latStr!!.toDouble()
                var lon: Double? = lonStr!!.toDouble()

                val locacion = Point.fromLngLat(lon!!, lat!!)

                PointAnnotation(point = locacion) {
                    iconImage = pinIcon
                }
            }
        }
    }
}
