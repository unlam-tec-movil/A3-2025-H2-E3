package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.AboutSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ActionButtons
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.GallerySection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.KeyInfo
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileHeader
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ProfileTab
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.TabSection

@Composable
fun ProfessionalProfileScreen(
    onBackClick: () -> Unit = {},
    onHowToGetThere: () -> Unit = {},
    onCall: () -> Unit = {},
    onWhatsApp: () -> Unit = {},
    onRegisterWork: () -> Unit = {},
    dni: Int,
    modifier: Modifier = Modifier,
) {
    // Estado para la pestaña seleccionada
    var selectedTab by remember { mutableStateOf(ProfileTab.ABOUT) }

    val professionalData =
        remember {
            ProfessionalData(
                name = "Carlos Rodríguez",
                profession = "Gasista Matriculado",
                rating = 4.8,
                aboutText =
                    "Gasista matriculado con más de 15 años de experiencia en instalaciones, " +
                        "reparaciones y mantenimiento de redes de gas en CABA y GBA. " +
                        "Ofrezco un servicio profesional, seguro y garantizado, " +
                        "cumpliendo con todas las normativas vigentes.",
                keyInfo =
                    listOf(
                        KeyInfo("Matrícula N°:", "2-12345-01"),
                        KeyInfo("Zona de Cobertura:", "CABA y GBA Norte"),
                        KeyInfo("Años de Experiencia:", "15+ años"),
                    ),
                services =
                    listOf(
                        "Instalaciones",
                        "Reparaciones",
                        "Detección de Fugas",
                        "Planos de Gas",
                        "Habilitaciones",
                    ),
            )
        }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                // Header con información básica
                ProfileHeader(
                    name = professionalData.name,
                    profession = professionalData.profession,
                    rating = professionalData.rating,
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
                )
            }

            // Contenido de la pestaña seleccionada
            when (selectedTab) {
                ProfileTab.ABOUT -> {
                    item {
                        AboutSection(
                            aboutText = professionalData.aboutText,
                            keyInfo = professionalData.keyInfo,
                            services = professionalData.services,
                        )
                    }
                }
                ProfileTab.GALLERY -> {
                    items(1) {
                        GallerySection()
                    }
                }
            }
        }
    }
}

data class ProfessionalData(
    val name: String,
    val profession: String,
    val rating: Double,
    val aboutText: String,
    val keyInfo: List<KeyInfo>,
    val services: List<String>,
)
