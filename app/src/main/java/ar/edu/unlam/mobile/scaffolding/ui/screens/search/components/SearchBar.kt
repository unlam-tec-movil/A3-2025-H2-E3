package ar.edu.unlam.mobile.scaffolding.ui.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.theme.TextBottomColorProfessional

@Preview
@Composable
fun viewSearchBar() {
    SearchBar(
        query = "",
        onQueryChange = { },
        active = true,
        onActiveChange = { },
        onBotonClick = { },
        borrarClick = { },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onBotonClick: () -> Unit,
    borrarClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        DockedSearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { onActiveChange(false) },
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(
                    "Search for professionals...",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                )
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .heightIn(0.dp, 150.dp),
            shape = RoundedCornerShape(0.dp),
        ) {
            // Sugerencias de búsqueda
            Text(
                "Sugerencias de búsqueda...",
                modifier = Modifier.padding(8.dp),
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        ButtonQuery(onBotonClick, borrarClick)
    }
}

@Composable
fun ButtonQuery(
    onBotonClick: () -> Unit,
    borrarClick: () -> Unit,
) {
    // Botones de acción (podrías moverlos a un menú de configuración)
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = onBotonClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(6.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = TextBottomColorProfessional,
                    contentColor = Color.White,
                ),
        ) {
            Text("CARGAR DATOS", fontWeight = FontWeight.Bold)
        }
        Button(
            onClick = borrarClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(6.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                ),
        ) {
            Text("BORRAR DATOS", fontWeight = FontWeight.Bold)
        }
    }
}
