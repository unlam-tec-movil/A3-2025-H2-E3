package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary

@Composable
fun AboutSection(
    aboutText: String,
    keyInfo: List<KeyInfo>,
    services: List<String>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Sección Acerca de mí
        Column {
            Text(
                text = "Acerca de mí",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = aboutText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp,
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        // Sección Información Clave
        Column {
            Text(
                text = "Información Clave",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            keyInfo.forEach { info ->
                KeyValueRow(
                    key = info.key,
                    value = info.value,
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        // Sección Servicios Ofrecidos
        Column {
            Text(
                text = "Servicios Ofrecidos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                services.forEach { service ->
                    ServiceChip(service = service)
                }
            }
        }
    }
}

@Composable
private fun KeyValueRow(
    key: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = key,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.wrapContentWidth(),
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun ServiceChip(service: String) {
    Box(
        modifier =
            Modifier
                .shadow(1.dp, RoundedCornerShape(16.dp), false, LightPrimary)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp),
                ).padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = service,
            fontSize = 12.sp,
            color = LightPrimary,
        )
    }
}

data class KeyInfo(
    val key: String,
    val value: String,
)
