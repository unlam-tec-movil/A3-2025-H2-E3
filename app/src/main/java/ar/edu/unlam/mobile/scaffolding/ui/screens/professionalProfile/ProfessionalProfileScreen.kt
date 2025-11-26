package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.AboutSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ActionButtons
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.FloatingMotionButton
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.KeyInfo
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileHeader
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileTab
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ReviewSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.TabSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.galery.GallerySection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.galery.ImageModal

@Composable
fun ProfessionalProfileScreen(
    onBackClick: () -> Unit = {},
    onHowToGetThere: () -> Unit = {},
    onCall: () -> Unit = {},
    onWhatsApp: () -> Unit = {},
    onRegisterWork: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ProfessionalProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    // Estado para la pestaña seleccionada
    var selectedTab by remember { mutableStateOf(ProfileTab.ABOUT) }

    // Estados para el modal de imagen
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }
    var isImageModalVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val uiStateReview by viewModel.uiStateReview.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
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
                    text = "Profesional",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            }
            Divider()
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
                                isProfileHV = uiState.professionals?.isProfileHV ?: false,
                                imgUrl = uiState.professionals?.imgUrl ?: "",
                            )

                            // Botones de acción
                            ActionButtons(
                                onHowToGetThere = onHowToGetThere,
                                onCall = onCall,
                                onWhatsApp = onWhatsApp,
                                onRegisterWork = onRegisterWork,
                            )

                            Spacer(modifier = Modifier.height(15.dp))
                            // Pestañas
                            TabSection(
                                selectedTab = selectedTab,
                                onTabSelected = { tab -> selectedTab = tab },
                                reloadReview = { viewModel.loadReviewsTab() },
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
                                }
                            }

                            ProfileTab.GALLERY -> {
                                items(1) {
                                    GallerySection(
                                        userIdGalery = uiState.professionals?.id ?: "",
                                        onImageClick = { imageUrl ->
                                            selectedImageUrl = imageUrl
                                            isImageModalVisible = true
                                        },
                                    )
                                }
                            }

                            ProfileTab.REVIEW -> {
                                items(1) {
                                    when {
                                        uiStateReview.isLoading -> {
                                            Column(
                                                modifier = Modifier.fillMaxSize(),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                CircularProgressIndicator()
                                                Text(
                                                    text = "Cargando reseñas...",
                                                    modifier = Modifier.padding(top = 16.dp),
                                                )
                                            }
                                        }

                                        else -> {
                                            ReviewSection(reviews = uiStateReview.reviews)
                                            Spacer(modifier = Modifier.height(80.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Icono flotante con animación de movimiento
        FloatingMotionButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp, 70.dp, 16.dp, 16.dp),
        )

        // Solo se muestra en la pestaña REVIEW
        if (selectedTab == ProfileTab.REVIEW) {
            FloatingActionButton(
                onClick = {
                    navController.navigate("review/${uiState.professionals?.id}")
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar reseña",
                )
            }
        }

        ImageModal(
            imageUrl = selectedImageUrl ?: "",
            isVisible = isImageModalVisible,
            onDismiss = {
                isImageModalVisible = false
                selectedImageUrl = null
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
