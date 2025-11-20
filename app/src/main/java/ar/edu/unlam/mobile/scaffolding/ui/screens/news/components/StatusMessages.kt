package ar.edu.unlam.mobile.scaffolding.ui.screens.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(
    error: String,
    onDismiss: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Button(
                onClick = onDismiss,
                modifier = Modifier.padding(top = 8.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Entendido", color = Color.White)
            }
        }
    }
}

@Composable
fun SuccessMessage() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.Green.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "¡Noticia publicada exitosamente!",
            color = Color.Green,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium,
        )
    }
}
