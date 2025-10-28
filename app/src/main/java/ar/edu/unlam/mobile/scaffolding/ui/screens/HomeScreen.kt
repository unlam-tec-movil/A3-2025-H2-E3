package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.PersonaEntity

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    SuccessScreen(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(vm: HomeViewModel) {
    val personas by vm.personas.collectAsState()

    Column(modifier = Modifier.padding(all = 10.dp)) {
        imagen()
        SearchBar(
            onBotonClick = { vm.insertarAll() },
            borrarClick = { vm.deleteAll() },
        )
        ListaResultados(personas)
    }
}

@Composable
fun imagen() {
    Image(
        painter = painterResource(R.drawable.casa),
        contentDescription = "casita",
        modifier =
            Modifier
                .fillMaxWidth()
                .size(230.dp),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onBotonClick: () -> Unit,
    borrarClick: () -> Unit,
) {
    // Estado para el texto de la búsqueda
    var query by remember { mutableStateOf("") }
    // Estado para ver si la barra está activa
    var active by remember { mutableStateOf(false) }

    DockedSearchBar(
        query = query, // El texto actual (vacío inicialmente)
        onQueryChange = { query = it }, // Función que actualiza el texto
        onSearch = { active = false }, // Función que se ejecuta al presionar Buscar
        active = active, // El estado de activación
        onActiveChange = { active = it }, // Función que actualiza el estado de activación
        placeholder = { Text("Buscar...") },
        modifier =
            Modifier
                .fillMaxWidth()
                .width(150.dp), // Texto temporal de ayuda
    ) {
        Text("...Sugerencias de búsqueda...")
    }
    Button(
        onClick = onBotonClick,
    ) {
        Text(text = "CARGAR DATOS")
        // carga datos iniciales de prueba en la BD
    }
    Button(
        onClick = borrarClick,
    ) {
        Text(text = "BORRAR DATOS")
    }
}

@Composable
fun ListaResultados(personas: List<PersonaEntity>) {
    Text("Lista de personas " + personas.size.toString())

    // filtra por personas de casanova, resta implementar
    // el filtro mediante busqueda con el search bar
    var listaFiltrada = personas.filter { it.ciudad == "Isidro Casanova" }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listaFiltrada) { it ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(text = it.nombre)
                Text(text = it.ciudad)
            }
        }
    }
}
