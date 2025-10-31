package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.theme.CardColorProfessional
import ar.edu.unlam.mobile.scaffolding.ui.theme.TextBottomColorProfessional

@Preview
@Composable
fun ViewTabSection() {
    TabSection(ProfileTab.GALLERY, {})
}

@Composable
fun TabSection(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableStateOf(selectedTab) }

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier.fillMaxWidth(),
        containerColor = CardColorProfessional,
        contentColor = Color.Gray,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                color = TextBottomColorProfessional,
                height = 3.dp,
            )
        },
    ) {
        ProfileTab.values().forEach { tab ->
            Tab(
                text = {
                    Text(
                        text = tab.title,
                        color = if (selectedTab == tab) TextBottomColorProfessional else Color.Gray,
                    )
                },
                selected = selectedTab == tab,
                onClick = {
                    selectedTab = tab
                    onTabSelected(tab)
                },
                selectedContentColor = Color.Blue, // Color cuando está seleccionado
                unselectedContentColor = Color.Gray, // Color cuando NO está seleccionado
            )
        }
    }
}

enum class ProfileTab(
    val title: String,
) {
    ABOUT("Acerca de"),
    GALLERY("Galería"),
}
