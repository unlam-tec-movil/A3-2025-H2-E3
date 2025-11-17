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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage

@SuppressLint("RestrictedApi")
@Composable
fun MapboxScreen(modifier: Modifier = Modifier) {
    val pinIcon =
        rememberIconImage(
            key = "red-marker",
            painter = painterResource(id = R.drawable.star_big_off), // your pin drawable
        )
    val puntoA = Point.fromLngLat(-58.3816, -34.6037)
    val view = LocalView.current

    val puntoB = Point.fromLngLat(-58.4500, -34.6500)

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
                text = "Mapa",
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
            PointAnnotation(point = puntoA) { iconImage = pinIcon } // Point 1
            PointAnnotation(point = puntoB) { iconImage = pinIcon } // Point 2
        }
    }
}
