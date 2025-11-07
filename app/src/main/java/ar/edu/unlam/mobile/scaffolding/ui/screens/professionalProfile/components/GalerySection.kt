package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    isMyProfile: Boolean = false,
    onAddItem: () -> Unit = {}, // Callback para agregar nuevo item
) {
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
                .then(
                    if (isMyProfile) {
                        Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp)
                    } else {
                        Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
                    },
                ),
    ) {
        if (isMyProfile) {
            // Scroll horizontal para mi perfil con botón agregar primero
            // Título de la sección
            Text(
                text = if (isMyProfile) "Mis Trabajos" else "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Primero el botón de agregar
                item {
                    AddGalleryItemCard(
                        onAddClick = onAddItem,
                        modifier =
                            Modifier
                                .width(150.dp)
                                .aspectRatio(0.9f),
                    )
                }

                // Luego los items existentes
                items(galleryItems) { item ->
                    GalleryItemCard(
                        item = item,
                        modifier =
                            Modifier
                                .width(150.dp)
                                .aspectRatio(0.9f),
                        isMyProfile,
                    )
                }
            }
        } else {
            // Grid 2 columnas para otros perfiles
            val chunkedItems = galleryItems.chunked(2)

            chunkedItems.forEachIndexed { index, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    rowItems.forEach { item ->
                        GalleryItemCard(
                            item = item,
                            modifier =
                                Modifier
                                    .weight(1f),
                            isMyProfile,
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                // Espacio entre filas
                if (index < chunkedItems.size - 1) {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Composable
fun AddGalleryItemCard(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        onClick = onAddClick,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Icono de +
            Box(
                modifier =
                    Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Agregar Trabajo",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun GalleryItemCard(
    item: GalleryItem,
    modifier: Modifier = Modifier,
    isMyProfile: Boolean,
) {
    Card(
        modifier =
            modifier
                .aspectRatio(0.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(if (isMyProfile) 8.dp else 0.dp),
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
