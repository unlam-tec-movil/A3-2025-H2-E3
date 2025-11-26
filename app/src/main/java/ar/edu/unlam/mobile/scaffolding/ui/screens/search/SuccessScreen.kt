package ar.edu.unlam.mobile.scaffolding.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.domain.model.Professionals
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.CardProfessionalSearch
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.CategoryFilterSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.search.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: SuccessViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Sincronizar la query del ViewModel con el estado local
    LaunchedEffect(query) {
        viewModel.setSearchQuery(query)
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Busqueda",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }
        HorizontalDivider()

        Column(modifier = Modifier.padding(12.dp, 12.dp, 12.dp, 0.dp)) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                active = active,
                onActiveChange = { active = it },
            )

            // Pasar el ViewModel al CategoryFilterSection
            CategoryFilterSection(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    viewModel.setCategoryFilter(category)
                },
            )

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = "Cargando profesionales...",
                            modifier = Modifier.padding(top = 16.dp),
                        )
                    }
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                        )
                        Button(
                            onClick = { viewModel.refreshProfessional() },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text("Reintentar")
                        }
                    }
                }

                else -> {
                    // Usar filteredProfessionals en lugar de filtrar manualmente
                    ListaResultados(
                        profesionales = uiState.filteredProfessionals,
                        onProfessionalClick = { profesional ->
                            navController.navigate("professional/${profesional.id}")
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun ListaResultados(
    profesionales: List<Professionals>,
    onProfessionalClick: (Professionals) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(profesionales) { profesional ->
            CardProfessionalSearch(
                P = profesional,
                onItemClick = onProfessionalClick,
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
