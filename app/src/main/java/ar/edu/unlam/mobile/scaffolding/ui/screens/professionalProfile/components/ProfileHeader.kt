package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.getOficioIcon
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary
import coil3.compose.AsyncImage

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    name: String,
    profession: String,
    rating: Double,
    isProfileHV: Boolean = false,
    imgUrl: String,
) {
    if (isProfileHV) {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageProfile(imgUrl = imgUrl, isMyProfile = isProfileHV)
            InfoProfile(name, profession, rating, isProfileHV)
        }
    } else {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(20.dp, 12.dp, 12.dp, 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            ImageProfile(imgUrl = imgUrl, isMyProfile = isProfileHV)
            InfoProfile(name, profession, rating, isProfileHV)
        }
    }
}

@Composable
fun ImageProfile(
    imgUrl: String? = null,
    isMyProfile: Boolean = false,
) {
    // Estados que se resetean cuando cambia la URL
    var isLoading by remember(imgUrl) { mutableStateOf(true) }
    var hasError by remember(imgUrl) { mutableStateOf(false) }
    var currentImageUrl by remember(imgUrl) { mutableStateOf(imgUrl) }

    // Efecto para manejar cambios en la URL
    LaunchedEffect(imgUrl) {
        if (imgUrl != currentImageUrl) {
            isLoading = true
            hasError = false
            currentImageUrl = imgUrl
        }
    }

    // URL final a cargar
    val finalImageUrl =
        if (!imgUrl.isNullOrEmpty()) {
            imgUrl
        } else {
            "https://cdn-icons-png.flaticon.com/512/9187/9187604.png"
        }

    Box(
        modifier =
            Modifier
                .size(if (isMyProfile) 150.dp else 110.dp)
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

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                strokeWidth = 3.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        // Si hay error, mostrar placeholder simple
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

@Composable
fun InfoProfile(
    name: String,
    profession: String,
    rating: Double,
    isMyProfile: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    start = if (isMyProfile) 0.dp else 25.dp,
                    top = if (isMyProfile) 10.dp else 0.dp,
                ),
        horizontalAlignment =
            if (isMyProfile) {
                Alignment.CenterHorizontally
            } else {
                Alignment.Start
            },
    ) {
        // Nombre y profesión
        Text(
            text = name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = profession,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        // Rating y reseñas
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Estrellas
            Row {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Estrella",
                        tint =
                            if (index < rating.toInt()) {
                                Color(0xFFFFC107)
                            } else {
                                Color(
                                    0xFFE0E0E0,
                                )
                            },
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            if (profession.isNotEmpty() && !isMyProfile) {
                Spacer(Modifier.width(20.dp))
                Icon(
                    imageVector = getOficioIcon(profession),
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
