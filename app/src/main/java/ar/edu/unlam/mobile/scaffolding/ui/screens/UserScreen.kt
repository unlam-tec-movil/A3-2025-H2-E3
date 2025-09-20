package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.ui.components.Greeting

@Composable
fun UserScreen(
    userId: String,
    modifier: Modifier = Modifier,
) {
    Greeting(userId, modifier)
}
