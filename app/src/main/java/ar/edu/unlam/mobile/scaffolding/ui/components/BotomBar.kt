package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import ar.edu.unlam.mobile.scaffolding.ui.screens.FORM_ROUTE

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar(modifier = Modifier.height(80.dp)) {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "home" } == true,
            onClick = { controller.navigate("home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "user/{id}" } == true,
            onClick = { controller.navigate("user/usuario") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == FORM_ROUTE } == true,
            onClick = { controller.navigate(FORM_ROUTE) },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "User",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
    }
}
