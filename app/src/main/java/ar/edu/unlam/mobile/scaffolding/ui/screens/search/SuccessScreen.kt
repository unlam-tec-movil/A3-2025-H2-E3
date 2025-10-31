package ar.edu.unlam.mobile.scaffolding.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaEntity
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.CardProfessionalSearch
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.CategoryFilterSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    vm: HomeViewModel,
    navController: NavController,
) { // Agregar NavController
    val personas by vm.personas.collectAsState()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(5.dp)) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            active = active,
            onActiveChange = { active = it },
            onBotonClick = { vm.insertarAll() },
            borrarClick = { vm.deleteAll() },
        )
        CategoryFilterSection()
        ListaResultados(
            personas,
            query,
            onProfessionalClick = { profesional ->
                // Navegar a la pantalla de perfil del profesional
                navController.navigate("professional/${profesional.dni}")
            },
        )
    }
}

@Composable
fun ListaResultados(
    personas: List<PersonaEntity>,
    parametroBusqueda: String,
    onProfessionalClick: (PersonaEntity) -> Unit, // Agregar este parámetro
) {
    Text(
        "Lista de personas " + personas.size.toString(),
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
    )

    var listaFiltrada =
        personas.filter { persona ->
            persona.oficios.any { oficio ->
                oficio.contains(
                    parametroBusqueda.trim(),
                    ignoreCase = true,
                )
            }
        }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(listaFiltrada) { persona ->
            CardProfessionalSearch(
                persona,
                onItemClick = onProfessionalClick,
            )
        }
    }
}
