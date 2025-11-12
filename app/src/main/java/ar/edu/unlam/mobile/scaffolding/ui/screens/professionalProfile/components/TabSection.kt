package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightGrayOverlay
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary

@Composable
fun TabSection(
    selectedTab: ProfileTab,
    onTabSelected: (ProfileTab) -> Unit,
    isMyProfile: Boolean = false,
    reloadReview: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier.fillMaxWidth(),
        containerColor = LightGrayOverlay,
        contentColor = Color.Gray,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                color = LightPrimary,
                height = 3.dp,
            )
        },
    ) {
        ProfileTab.values().forEach { tab ->
            if (isMyProfile) {
                if (tab !== ProfileTab.GALLERY) {
                    Tab(
                        text = {
                            Text(
                                text = tab.title,
                                color = if (selectedTab == tab) LightPrimary else Color.Gray,
                            )
                        },
                        selected = selectedTab == tab,
                        onClick = {
                            onTabSelected(tab)
                        },
                    )
                }
            } else {
                if (tab == ProfileTab.REVIEW) {
                    Tab(
                        text = {
                            Text(
                                text = tab.title,
                                color = if (selectedTab == tab) LightPrimary else Color.Gray,
                            )
                        },
                        selected = selectedTab == tab,
                        onClick = {
                            onTabSelected(tab)
                            reloadReview()
                        },
                    )
                } else {
                    Tab(
                        text = {
                            Text(
                                text = tab.title,
                                color = if (selectedTab == tab) LightPrimary else Color.Gray,
                            )
                        },
                        selected = selectedTab == tab,
                        onClick = {
                            onTabSelected(tab)
                        },
                    )
                }
            }
        }
    }
}

enum class ProfileTab(
    val title: String,
) {
    ABOUT("Acerca de"),
    REVIEW("Reseña"),
    GALLERY("Galería"),
}
