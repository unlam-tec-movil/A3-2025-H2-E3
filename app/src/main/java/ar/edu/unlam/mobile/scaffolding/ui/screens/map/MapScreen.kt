package ar.edu.unlam.mobile.scaffolding.ui.screens.map

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapScreen() {
    Text(
        text = "SCREEN MAP",
        fontSize = 34.sp,
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 1,
    )
}
