package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.galery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun GalleryItemCard(
    imageUrl: String,
    modifier: Modifier = Modifier,
    isProfileHV: Boolean,
    onDeleteClick: (() -> Unit)? = null,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(if (isProfileHV) 8.dp else 5.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .aspectRatio(0.9f),
        ) {
            // Usar coil para cargar imagen desde URL
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagen de trabajo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Botón de eliminar (solo visible en perfil propio y cuando hay función de eliminación)
            if (onDeleteClick != null) {
                IconButton(
                    onClick = onDeleteClick,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar imagen",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}
