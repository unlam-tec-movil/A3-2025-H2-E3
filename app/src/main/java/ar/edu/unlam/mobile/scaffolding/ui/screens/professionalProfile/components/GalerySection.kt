package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun GallerySection(modifier: Modifier = Modifier) {
    val galleryItems =
        remember {
            listOf(
                GalleryItem("Instalación", "de Cañerías", R.drawable.plomeria),
                GalleryItem("Cambio", "de Sistema", R.drawable.gas),
                GalleryItem("Instalación", "de Tablero", R.drawable.tablero),
                GalleryItem("Reparación", "de Muebles", R.drawable.carpintero),
                GalleryItem("Limpieza", "Profunda", R.drawable.limpieza),
                GalleryItem("Afilado", "de Herramientas", R.drawable.work1),
            )
        }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        // Grid manual con Column y Row
        val chunkedItems = galleryItems.chunked(2)

        chunkedItems.forEachIndexed { index, rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier.weight(1f),
                    ) {
                        GalleryItemCard(item = item)
                    }
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            if (index < chunkedItems.size - 1) {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun GalleryItemCard(item: GalleryItem) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (item.subtitle.isNotEmpty()) {
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

data class GalleryItem(
    val title: String,
    val subtitle: String,
    val imageRes: Int,
)
