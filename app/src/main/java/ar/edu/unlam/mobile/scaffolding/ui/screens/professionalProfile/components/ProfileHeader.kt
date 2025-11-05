package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Preview
@Composable
fun ViewProfileHeader() {
    ProfileHeader("Roberto García", "Gasista Matriculado", 4.8)
}

@Composable
fun ProfileHeader(
    name: String,
    profession: String,
    rating: Double,
    isMyProfile: Boolean = false,
    modifier: Modifier = Modifier,
) {
    if (isMyProfile) {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageProfile(isMyProfile = isMyProfile)
            InfoProfile(name, profession, rating, isMyProfile)
        }
    } else {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            ImageProfile(isMyProfile = isMyProfile)
            InfoProfile(name, profession, rating, isMyProfile)
        }
    }
}

@Composable
fun ImageProfile(
    imgUrl: String = "https://cdn-icons-png.flaticon.com/512/9187/9187604.png",
    isMyProfile: Boolean = false,
) {
    Box(
        modifier =
            Modifier
                .size(if (isMyProfile) 120.dp else 90.dp)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .clip(CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imgUrl,
            contentDescription = "imagen",
            contentScale = ContentScale.Crop,
        )
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
        }
    }
}
