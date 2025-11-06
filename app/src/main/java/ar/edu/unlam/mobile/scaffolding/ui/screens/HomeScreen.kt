package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.SuccessScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    Box(modifier = modifier.fillMaxSize()) {
        SuccessScreen(viewModel, navController) // Pasar el navController
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(vm: HomeViewModel) {
    val personas by vm.personas.collectAsState()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(all = 10.dp)) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            active = active,
            onActiveChange = { active = it },
            onBotonClick = { vm.insertarAll() },
            borrarClick = { vm.deleteAll() },
        )
        ListaResultados(personas, query)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    query: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onBotonClick: () -> Unit,
    borrarClick: () -> Unit,
) {
    DockedSearchBar(
        query = query, // El texto actual (vacío inicialmente)
        onQueryChange = onQueryChange, // actualiza el texto
        onSearch = { onActiveChange(false) }, // ejecuta al presionar Buscar
        active = active, // El estado de activación
        onActiveChange = onActiveChange, // actualiza el estado de activación
        placeholder = { Text("Buscar...") },
        modifier =
            Modifier
                .fillMaxWidth()
                .width(150.dp),
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
fun ListaResultados(
    personas: List<PersonaEntity>,
    parametroBusqueda: String,
) {
    Text("Lista de personas " + personas.size.toString())
    // var parametro: String = ""
    var listaFiltrada =
        personas.filter { persona ->

            persona.oficios.any { oficio ->
                oficio.contains(
                    parametroBusqueda.trim(),
                    ignoreCase = true,
                )
            }
        }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listaFiltrada) { it ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(text = it.nombre)
                Text(text = it.ciudad)
                Text(text = it.oficios.toString())
            }
        }
    }
}
*/
