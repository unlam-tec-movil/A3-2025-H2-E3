package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewSection(modifier: Modifier = Modifier) {
    val reviews =
        listOf(
            Review(
                clientName = "Maria G.",
                date = "15 de Oct, 2023",
                rating = 5,
                comment =
                    "Juan was fantastic!" +
                        " He arrived on time, was very professional," +
                        " and fixed my leaky faucet in no time. " +
                        " Highly recommend his services to anyone in Buenos Aires.",
            ),
            Review(
                clientName = "Carlos R.",
                date = "12 de Oct, 2023",
                rating = 4,
                comment =
                    "Good service, but he was a little late." +
                        " The work was done well and the price was fair." +
                        "  I would use him again.",
            ),
            Review(
                clientName = "Sofia L.",
                date = "09 de Oct, 2023",
                rating = 5,
                comment =
                    "¡Excelente servicio! Juan es muy amable y profesional." +
                        " Resolvió el problema de mi cañería rápidamente. " +
                        "¡Lo llamaré de nuevo si lo necesito!",
            ),
        )

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        reviews.forEachIndexed { index, review ->
            ReviewCard(review = review)
            Divider(modifier = Modifier.background(color = Color.Gray).fillMaxWidth())
            if (index < reviews.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
        ) {
            // Header con nombre, fecha y rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = review.clientName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = review.date,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Estrellas de rating
            Row {
                repeat(5) { index ->
                    Text(
                        text = if (index < review.rating) "★" else "☆",
                        color =
                            if (index < review.rating) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                        fontSize = 16.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Comentario
            Text(
                text = review.comment,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp,
            )
        }
    }
}

data class Review(
    val clientName: String,
    val date: String,
    val rating: Int, // 1-5 estrellas
    val comment: String,
)
