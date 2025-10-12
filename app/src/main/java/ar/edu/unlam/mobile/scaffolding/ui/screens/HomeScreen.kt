package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.Greeting

const val HOME_SCREEN_ROUTE = "home"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // La información que obtenemos desde el view model la consumimos a través de un estado de
    // "tres vías": Loading, Success y Error. Esto nos permite mostrar un estado de carga,
    // un estado de éxito y un mensaje de error.
    val uiState: HomeUIState by viewModel.uiState.collectAsState()

    when (val helloState = uiState.helloMessageState) {
        is HelloMessageUIState.Loading -> {
            // Loading
        }

        is HelloMessageUIState.Success -> {
            Column {
                Greeting(helloState.message, modifier)
              Success()
            }
        }

        is HelloMessageUIState.Error -> {
            // Error
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Success(){
    Column {
        imagen()
        SearchBar()
        ListaResultados()
    }


}

@Composable
fun imagen(){
    Image(
        painter = painterResource(R.drawable.casa),
        contentDescription = "casita"
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(){
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

        placeholder = { Text("Buscar...") } // Texto temporal de ayuda
    ) {
        Text("...Sugerencias de búsqueda...")
    }
}
@Composable
fun ListaResultados(){

}






