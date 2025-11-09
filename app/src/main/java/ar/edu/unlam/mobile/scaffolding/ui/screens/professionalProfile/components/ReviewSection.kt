package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.model.Reviews
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReviewSection(
    modifier: Modifier = Modifier,
    reviews: List<Reviews> = emptyList(),
) {
    if (reviews.isNotEmpty()) {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
        ) {
            reviews.forEachIndexed { index, review ->
                ReviewCard(review = review)
                if (index < reviews.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    } else {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Sin Reseñas Encontradas",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun ReviewCard(review: Reviews) {
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
                    text = review.userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = getRelativeTime(review.createdAt.seconds),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            // Estrellas de rating
            Row {
                repeat(5) { index ->
                    Text(
                        text = if (index < review.stars) "★" else "☆",
                        color =
                            if (index < review.stars) {
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
                text = review.message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp,
            )
        }
    }
}

fun getRelativeTime(seconds: Long): String {
    val reviewTime = seconds * 1000
    val now = System.currentTimeMillis()
    val diff = now - reviewTime

    val secondsDiff = diff / 1000
    val minutesDiff = secondsDiff / 60
    val hoursDiff = minutesDiff / 60
    val daysDiff = hoursDiff / 24
    val weeksDiff = daysDiff / 7
    val monthsDiff = daysDiff / 30

    return when {
        secondsDiff < 60 -> "Hace unos segundos"
        minutesDiff < 60 -> "Hace $minutesDiff minuto${if (minutesDiff > 1) "s" else ""}"
        hoursDiff < 24 -> "Hace $hoursDiff hora${if (hoursDiff > 1) "s" else ""}"
        daysDiff == 1L -> "Ayer"
        daysDiff < 7 -> "Hace $daysDiff días"
        weeksDiff < 4 -> "Hace $weeksDiff semana${if (weeksDiff > 1) "s" else ""}"
        monthsDiff < 12 -> "Hace $monthsDiff mes${if (monthsDiff > 1) "es" else ""}"
        else -> {
            val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault())
            formatter.format(Date(reviewTime))
        }
    }
}
