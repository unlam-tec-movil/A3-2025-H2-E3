package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun ViewTabSection(){
    TabSection(ProfileTab.GALLERY, {})
}


@Composable
fun TabSection(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(selectedTab) }

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier.fillMaxWidth(),
        contentColor = Color.Gray
    ) {
        ProfileTab.values().forEach { tab ->
            Tab(
                text = { Text(tab.title) },
                selected = selectedTab == tab,
                onClick = {
                    selectedTab = tab
                    onTabSelected(tab)
                }
            )
        }
    }
}

enum class ProfileTab(val title: String) {
    ABOUT("Acerca de"),
    GALLERY("Galería")
}