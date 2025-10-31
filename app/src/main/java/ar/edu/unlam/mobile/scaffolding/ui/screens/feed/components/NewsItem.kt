package ar.edu.unlam.mobile.scaffolding.ui.screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun ViewNewItem() {
    NewsItem(
        "Juan Pérez",
        "Plomero",
        "Instalación de plomería completa terminada hoy en Palermo! Trabajo limpio y clientes felices.",
    )
}

@Composable
fun NewsItem(
    name: String,
    profession: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Divider()
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
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
                                .background(Color.Blue, CircleShape)
                                .clip(CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        // agarro la primera letra pero la idea es poner imagen despues
                        Text(
                            text = name.take(1),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Información del profesional
                    Column {
                        Text(
                            text = name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black,
                        )
                        Text(
                            text = profession,
                            fontSize = 14.sp,
                            color = Color.Gray,
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
                        tint = Color.Gray,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mensaje de la novedad
            Text(
                text = message,
                fontSize = 14.sp,
                color = Color.Black,
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter =
                    painterResource(
                        id =
                            if (name == "Juan Pérez") {
                                R.drawable.plomeria
                            } else if (name == "María Rodríguez") {
                                R.drawable.tablero
                            } else {
                                R.drawable.gas
                            },
                    ),
                contentDescription = "imagen",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(8.dp))

            ServiceButton({}, modifier = Modifier.padding(0.dp))
        }
    }
}
