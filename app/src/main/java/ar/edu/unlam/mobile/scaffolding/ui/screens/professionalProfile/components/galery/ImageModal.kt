package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.galery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ImageModal(
    imageUrl: String,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter =
            fadeIn(animationSpec = tween(300)) +
                scaleIn(
                    initialScale = 0.8f,
                    animationSpec = tween(300),
                ),
        exit =
            fadeOut(animationSpec = tween(300)) +
                scaleOut(
                    targetScale = 0.8f,
                    animationSpec = tween(300),
                ),
        modifier = modifier,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.95f))
                    .clickable { onDismiss() },
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(0.95f)
                        .align(Alignment.Center)
                        .clickable(enabled = false, onClick = {}),
            ) {
                // Botón de cerrar
                IconButton(
                    onClick = onDismiss,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar imagen ampliada",
                        tint = Color.White,
                    )
                }

                // Imagen
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Imagen ampliada",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.align(Alignment.Center).fillMaxSize(),
                )
            }
        }
    }
}
