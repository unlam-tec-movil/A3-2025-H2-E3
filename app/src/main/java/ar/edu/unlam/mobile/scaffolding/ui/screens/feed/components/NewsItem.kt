package ar.edu.unlam.mobile.scaffolding.ui.screens.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.getRelativeTime
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    name: String,
    profession: String,
    message: String,
    isLiked: Boolean = false,
    imgUrl: String = "",
    userImgUrl: String = "",
    createdAt: Long = 0L,
    onProfessionalClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        HorizontalDivider()
        Column(
            modifier =
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp),
        ) {
            // Header con nombre y profesión
            Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onProfessionalClick)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(0.8f),
                ) {
                    // Avatar
                    Box(
                        modifier =
                            Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = userImgUrl,
                            contentDescription = "imagen de perfil del profesional",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Información del profesional
                    Column {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = profession,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
                Text(
                    text = getRelativeTime(createdAt),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mensaje de la novedad
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 20.sp,
            )

            if (imgUrl.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                // Imagen de la publicación
                AsyncImage(
                    model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(imgUrl)
                            .crossfade(true)
                            .build(),
                    contentDescription = "imagen de la novedad",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Reemplazar con el componente de botón de servicio si existe
            // ServiceButton(onProfessionalClick, modifier = Modifier.padding(0.dp))
        }
    }
}
