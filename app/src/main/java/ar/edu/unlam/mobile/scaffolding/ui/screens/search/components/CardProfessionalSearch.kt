package ar.edu.unlam.mobile.scaffolding.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary
import coil3.compose.AsyncImage

@Composable
fun CardProfessionalSearch(
    P: Professionals,
    onItemClick: (Professionals) -> Unit = {},
) {
    HorizontalDivider()
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onItemClick(P) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar con la primera letra del nombre
            ImgCardProfessional(P.imgUrl)

            Spacer(modifier = Modifier.width(16.dp))

            // Información del profesional
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = P.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = getOficioDisplayName(P.profession),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                    )

                    // Rating
                    Text(
                        text = "⭐ ${P.rating}",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Iconos según los oficios
            if (P.profession.isNotEmpty()) {
                Icon(
                    imageVector = getOficioIcon(P.profession),
                    contentDescription = null,
                    tint = LightPrimary,
                    modifier =
                        Modifier
                            .size(32.dp)
                            .padding(horizontal = 2.dp),
                )
            }
        }
    }
}

@Composable
fun ImgCardProfessional(imgUrl: String) {
    var isLoading by remember(imgUrl) { mutableStateOf(true) }
    var hasError by remember(imgUrl) { mutableStateOf(false) }
    var currentImageUrl by remember(imgUrl) { mutableStateOf(imgUrl) }

    LaunchedEffect(imgUrl) {
        if (imgUrl != currentImageUrl) {
            isLoading = true
            hasError = false
            currentImageUrl = imgUrl
        }
    }

    val finalImageUrl =
        if (!imgUrl.isNullOrEmpty()) {
            imgUrl
        } else {
            "https://cdn-icons-png.flaticon.com/512/9187/9187604.png"
        }

    Box(
        modifier =
            Modifier
                .size(50.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .clip(CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = finalImageUrl,
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            onLoading = {
                isLoading = true
                hasError = false
            },
            onSuccess = {
                isLoading = false
                hasError = false
            },
            onError = {
                isLoading = false
                hasError = true
            },
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                strokeWidth = 3.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        if (hasError && !isLoading) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Imagen no disponible",
                modifier =
                    Modifier
                        .size(60.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            CircleShape,
                        ).padding(12.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

// Función para obtener el icono según el oficio
fun getOficioIcon(oficio: String): ImageVector =
    when (oficio.lowercase()) {
        "plomería" -> Icons.Default.Build
        "electricidad" -> Icons.Default.FlashOn
        "carpintería" -> Icons.Default.ContentCut
        "pintura" -> Icons.Default.Brush
        "jardinería" -> Icons.Default.Nature
        "electricidad automotriz" -> Icons.Default.DirectionsCar
        "reparación informática" -> Icons.Default.Computer
        "cerrajería" -> Icons.Default.Lock
        "gas" -> Icons.Default.LocalFireDepartment
        "albañilería" -> Icons.Default.Handyman
        "mecánica" -> Icons.Default.Engineering
        "clima" -> Icons.Default.AcUnit
        else -> Icons.Default.Person
    }

// Función para obtener el nombre display del oficio
fun getOficioDisplayName(oficio: String): String =
    when (oficio.lowercase()) {
        "plomería" -> "Plomería"
        "electricidad" -> "Electricidad"
        "carpintería" -> "Carpintería"
        "pintura" -> "Pintura"
        "jardinería" -> "Jardinería"
        "electricidad automotriz" -> "Electricidad Automotriz"
        "reparación informática" -> "Reparación Informática"
        "cerrajería" -> "Cerrajería"
        "gas" -> "Gas"
        "albañilería" -> "Albañilería"
        "mecánica" -> "Mecánica"
        "clima" -> "Clima"
        else -> oficio
    }
