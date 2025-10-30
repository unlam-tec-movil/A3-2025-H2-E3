package ar.edu.unlam.mobile.scaffolding.ui.screens.feed


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.ui.screens.feed.components.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FeedScreen(
    onServiceRequest: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Novedades hardcodeadas
    val newsItems = remember {
        listOf(
            NewsData(
                id = 1,
                name = "Juan Pérez",
                profession = "Plomero",
                message = "Instalación de plomería completa terminada hoy en Palermo! Trabajo limpio y clientes felices.",
                isLiked = false
            ),
            NewsData(
                id = 2,
                name = "María Rodríguez",
                profession = "Electricista",
                message = "Consejo rápido: revisa los interruptores de tu tablero eléctrico antes de llamar. ¡Podrías ahorrarte el costo de la visita!",
                isLiked = false
            ),
            NewsData(
                id = 3,
                name = "Carlos Gomez",
                profession = "Gasista Matriculado",
                message = "Revisión de seguridad de una instalación de gas en Caballito, ¡La seguridad es siempre lo primero! Contactame para revisiones anuales.",
                isLiked = false
            )
        )
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Novedades",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(Color(0xFFF5F5F5))
        ) {
            items(newsItems) { item ->
                NewsItem(
                    name = item.name,
                    profession = item.profession,
                    message = item.message,
                )
            }
        }
    }
}


data class NewsData(
    val id: Int,
    val name: String,
    val profession: String,
    val message: String,
    val isLiked: Boolean
)