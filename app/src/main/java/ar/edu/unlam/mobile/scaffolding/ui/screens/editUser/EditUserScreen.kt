package ar.edu.unlam.mobile.scaffolding.ui.screens.editUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.components.KeyDetailsSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.components.ProfileInputField
import ar.edu.unlam.mobile.scaffolding.ui.screens.editUser.components.ServicesSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.ImageProfile
import ar.edu.unlam.mobile.scaffolding.ui.theme.LightPrimary

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditUserViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    // Manejar estados de carga y error
    when {
        uiState.isLoading -> {
            LoadingScreen()
            return
        }
        uiState.error != null -> {
            ErrorScreen(
                error = uiState.error!!,
                onRetry = { viewModel.refreshProfessional() },
            )
            return
        }
    }

    val professional = uiState.professionals

    // Estados locales para los campos editables
    var imgUrl by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var profession by remember { mutableStateOf("") }
    var aboutMe by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var licenseNumber by remember { mutableStateOf("") }
    var services by remember { mutableStateOf(emptyList<String>()) }

    // Actualizar los estados cuando lleguen los datos del profesional
    LaunchedEffect(professional) {
        if (professional != null) {
            imgUrl = professional.imgUrl
            fullName = professional.name ?: ""
            profession = professional.profession ?: ""
            aboutMe = professional.aboutText ?: ""
            location = professional.keyInfo?.get("Zona de Cobertura") ?: ""
            experience = professional.keyInfo?.get("años de experiencia") ?: ""
            licenseNumber = professional.keyInfo?.get("Matricula") ?: ""
            services = professional.services ?: emptyList()
        }
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(0.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Editar Perfil",
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }
        HorizontalDivider()

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box {
                        ImageProfile(isMyProfile = true, imgUrl = imgUrl)
                        Box(
                            modifier =
                                Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .align(Alignment.BottomEnd)
                                    .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Change photo",
                                tint = LightPrimary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
            }

            HorizontalDivider()

            Text(
                text = "User Data",
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp),
            )

            ProfileInputField(
                label = "Full Name",
                value = fullName,
                onValueChange = { fullName = it },
                modifier = Modifier.padding(bottom = 20.dp),
            )

            ProfileInputField(
                label = "Profession",
                value = profession,
                onValueChange = { profession = it },
                modifier = Modifier.padding(bottom = 0.dp),
            )

            ProfileInputField(
                label = "About Me",
                value = aboutMe,
                onValueChange = { aboutMe = it },
                singleLine = false,
                maxLines = 3,
                modifier = Modifier.padding(vertical = 20.dp),
            )

            HorizontalDivider()

            KeyDetailsSection(
                location = location,
                experience = experience,
                licenseNumber = licenseNumber,
                onLocationChange = { location = it },
                onExperienceChange = { experience = it },
                onLicenseChange = { licenseNumber = it },
                modifier = Modifier.padding(vertical = 15.dp),
            )

            HorizontalDivider()

            ServicesSection(
                services = services,
                onServicesChanged = { services = it },
                modifier = Modifier.padding(vertical = 10.dp),
            )

            Button(
                onClick = {
                    // Guardar cambios
                    viewModel.saveChanges(
                        fullName = fullName,
                        profession = profession,
                        aboutMe = aboutMe,
                        location = location,
                        experience = experience,
                        licenseNumber = licenseNumber,
                        services = services,
                    )
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                    ),
            ) {
                Text(
                    text = "Save Changes",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                        ),
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CircularProgressIndicator()
            Text(
                text = "Cargando datos...",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ErrorScreen(
    error: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Error al cargar los datos",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
        )
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp),
        )
        Button(
            onClick = onRetry,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
        ) {
            Text("Reintentar")
        }
    }
}
