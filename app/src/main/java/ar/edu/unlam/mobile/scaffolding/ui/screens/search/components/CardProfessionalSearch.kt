package ar.edu.unlam.mobile.scaffolding.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaEntity
import ar.edu.unlam.mobile.scaffolding.ui.theme.TextBottomColorProfessional

@Preview
@Composable
fun viewCardProfessionalSearch() {
    val personaEjemplo =
        PersonaEntity(
            nombre = "Alejandro Gomez",
            dni = 46872596,
            profesional = true,
            ubicacion = "0",
            ciudad = "Ciudad Ejemplo",
            oficios = listOf("plumber"),
        )

    CardProfessionalSearch(personaEjemplo)
}

@Composable
fun CardProfessionalSearch(
    P: PersonaEntity,
    onItemClick: (PersonaEntity) -> Unit = {},
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onItemClick(P) },
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar con la primera letra del nombre
            Box(
                modifier =
                    Modifier
                        .size(50.dp)
                        .background(Color.Gray, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text =
                        P.nombre
                            .first()
                            .toString()
                            .uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del profesional
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = P.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = getOficioDisplayName(P.oficios.first()),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                    )

                    Text(
                        text = " - ${P.ciudad}",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Iconos según los oficios - solo el primero por ahora
            if (P.oficios.isNotEmpty()) {
                Icon(
                    imageVector = getOficioIcon(P.oficios.first()),
                    contentDescription = null,
                    tint = TextBottomColorProfessional,
                    modifier =
                        Modifier
                            .size(32.dp)
                            .padding(horizontal = 2.dp),
                )
            }
        }
    }
}

// Función para obtener el icono según el oficio
fun getOficioIcon(oficio: String): ImageVector =
    when (oficio.lowercase()) {
        "plomería" -> Icons.Default.Build
        "electricidad" -> Icons.Default.FlashOn
        "carpintería" -> Icons.Default.ContentCut
        "pintura" -> Icons.Default.Brush
        "jardinería" -> Icons.Default.Nature
        "electricidad automotriz" -> Icons.Default.DirectionsCar
        "reparación informática" -> Icons.Default.Computer
        "cerrajería" -> Icons.Default.Lock
        "gas" -> Icons.Default.LocalFireDepartment
        "albañilería" -> Icons.Default.Handyman
        "mecánica" -> Icons.Default.Engineering
        "clima" -> Icons.Default.AcUnit
        else -> Icons.Default.Person
    }

// Función para obtener el nombre display del oficio
fun getOficioDisplayName(oficio: String): String =
    when (oficio.lowercase()) {
        "plomería" -> "Plomería"
        "electricidad" -> "Electricidad"
        "carpintería" -> "Carpintería"
        "pintura" -> "Pintura"
        "jardinería" -> "Jardinería"
        "electricidad automotriz" -> "Electricidad Automotriz"
        "reparación informática" -> "Reparación Informática"
        "cerrajería" -> "Cerrajería"
        "gas" -> "Gas"
        "albañilería" -> "Albañilería"
        "mecánica" -> "Mecánica"
        "clima" -> "Clima"
        else -> oficio
    }
