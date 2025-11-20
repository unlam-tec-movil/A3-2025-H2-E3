package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar(modifier = Modifier.height(70.dp).padding(0.dp)) {
        // boton de feed
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "feed" } == true,
            onClick = { controller.navigate("feed") },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Newspaper,
                    contentDescription = "feed",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(45.dp).padding(0.dp),
                )
            },
        )
        // boton de busqueda
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "home" } == true,
            onClick = { controller.navigate("home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(45.dp).padding(0.dp),
                )
            },
        )
        // boton de mapa
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.route == "map",
            onClick = { controller.navigate("map") },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = "map",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(45.dp).padding(10.dp),
                )
            },
        )
        // boton de perfil
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "form" } == true,
            onClick = { controller.navigate("form") },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.height(45.dp).padding(0.dp),
                )
            },
        )
    }
}
