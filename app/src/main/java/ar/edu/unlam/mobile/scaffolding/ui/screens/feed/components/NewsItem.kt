package ar.edu.unlam.mobile.scaffolding.ui.screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter

@Composable
fun NewsItem(
    modifier: Modifier = Modifier,
    name: String,
    profession: String,
    message: String,
    isLiked: Boolean = false,
    imgUrl: String = "",
    userImgUrl: String = "",
) {
    val painter = rememberAsyncImagePainter(model = imgUrl)
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
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
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(0.8f),
                ) {
                    // Avatar
                    Box(
                        modifier =
                            Modifier
                                .size(40.dp)
                                .background(Color.Gray, CircleShape)
                                .clip(CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = userImgUrl,
                            contentDescription = "imagen",
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(),
                            contentScale = ContentScale.Crop,
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
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Dar like",
                        tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.Gray,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mensaje de la novedad
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    // Mostrar spinner mientras carga
                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .size(48.dp),
                        strokeWidth = 3.dp,
                        color = Color.White,
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    // Mostrar icono de error si falla la carga
                    Image(
                        painter = painterResource(R.drawable.error),
                        contentDescription = "imagen",
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }

                else -> {
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = "imagen",
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            ServiceButton({}, modifier = Modifier.padding(0.dp))
        }
    }
}
