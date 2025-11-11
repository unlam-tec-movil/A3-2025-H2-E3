package ar.edu.unlam.mobile.scaffolding.ui.screens.news.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsContentSection(
    content: String,
    onContentChange: (String) -> Unit,
) {
    Column {
        Text(
            text = "Escribe tu noticia",
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 16.sp,
        )

        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            placeholder = {
                Text(
                    text = "Comparte detalles sobre tu trabajo, consejos o promociones...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
            singleLine = false,
            maxLines = 10,
        )
    }
}
