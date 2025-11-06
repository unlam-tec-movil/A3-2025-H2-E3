package ar.edu.unlam.mobile.scaffolding.ui.screens.profile

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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.AboutSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.GallerySection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.KeyInfo
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileHeader
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileTab
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ReviewSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.TabSection

@Preview
@Composable
fun ViewProfileScreen() {
    ProfileScreen(1)
}

@Composable
fun ProfileScreen(
    dni: Int,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    // Estado para la pestaña seleccionada
    var selectedTab by remember { mutableStateOf(ProfileTab.ABOUT) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
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
                text = "Perfil Profesional",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }

        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Cargando Perfil...",
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
                Divider()
                LazyColumn(
                    modifier = Modifier.weight(1f),
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        // Header con información básica
                        ProfileHeader(
                            modifier = Modifier,
                            name = uiState.professionals?.name ?: "",
                            profession = uiState.professionals?.profession ?: "",
                            rating = uiState.professionals?.rating ?: 1.0,
                            isMyProfile = true,
                            imgUrl = uiState.professionals?.imgUrl ?: "",
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                        // Pestañas
                        TabSection(
                            isMyProfile = true,
                            selectedTab = selectedTab,
                            onTabSelected = { tab -> selectedTab = tab },
                        )
                    }

                    // Contenido de la pestaña seleccionada
                    when (selectedTab) {
                        ProfileTab.ABOUT -> {
                            item {
                                AboutSection(
                                    aboutText = uiState.professionals?.aboutText ?: "",
                                    keyInfo =
                                        uiState.professionals?.keyInfo?.map { (key, value) ->
                                            KeyInfo(key, value)
                                        } ?: emptyList(),
                                    services = uiState.professionals?.services ?: emptyList(),
                                    isMyProfile = true,
                                )
                                GallerySection(isMyProfile = true)
                            }
                        }

                        ProfileTab.GALLERY -> {
                            items(1) {
                            }
                        }

                        ProfileTab.REVIEW -> {
                            items(1) {
                                ReviewSection()
                            }
                        }
                    }
                }
            }
        }
    }
}
