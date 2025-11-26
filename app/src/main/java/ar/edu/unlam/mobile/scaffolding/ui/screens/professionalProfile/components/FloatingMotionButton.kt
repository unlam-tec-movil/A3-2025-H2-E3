package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import kotlinx.coroutines.delay

@Composable
fun FloatingMotionButton(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.White,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    size: Int = 52,
    elevation: Int = 8,
) {
    // Animación de rotación que simula movimiento del celular
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            rotation.animateTo(
                targetValue = 25f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            )

            rotation.animateTo(
                targetValue = -25f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            )

            rotation.animateTo(
                targetValue = 25f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            )

            rotation.animateTo(
                targetValue = -25f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            )

            rotation.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            )

            delay(1500)
        }
    }

    Box(
        modifier =
            modifier
                .size(size.dp)
                .shadow(elevation.dp, CircleShape)
                .clip(CircleShape)
                .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.pthone),
            contentDescription = "Mover el dispositivo",
            modifier =
                Modifier
                    .size((size * 0.6).dp)
                    .rotate(rotation.value),
            tint = iconColor,
        )
    }
}
