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
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(all = 10.dp)) {
        imagen()
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
    var listaFiltrada = personas.filter { it.ciudad.contains(parametroBusqueda.trim(), ignoreCase = true) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listaFiltrada) { it ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(text = it.nombre)
                Text(text = it.ciudad)
            }
        }
    }
}
