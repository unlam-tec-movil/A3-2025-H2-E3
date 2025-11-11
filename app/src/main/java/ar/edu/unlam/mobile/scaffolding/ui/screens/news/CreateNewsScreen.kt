package ar.edu.unlam.mobile.scaffolding.ui.screens.news

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.createnews.CreateNewsViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.components.ImageUploadSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.components.NewsContentSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.components.PublishButton
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.components.SuccessMessage
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.components.UserInfoSection
import ar.edu.unlam.mobile.scaffolding.ui.screens.profile.ProfileViewModel

@Composable
fun CreateNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateNewsViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onPublishSuccess: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateProfile by profileViewModel.uiState.collectAsState()

    // Launcher para seleccionar imagen de la galería
    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                viewModel.updateSelectedImage(uri)
            },
        )

    // Pasar los datos del profesional al ViewModel
    LaunchedEffect(uiStateProfile.professionals) {
        uiStateProfile.professionals?.let { professional ->
            viewModel.setProfessionalData(professional)
        }
    }

    // Manejar éxito de publicación
    LaunchedEffect(uiState.publishSuccess) {
        if (uiState.publishSuccess) {
            kotlinx.coroutines.delay(2000)
            viewModel.resetForm()
            onPublishSuccess()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        // Header
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
                text = "Subir Noticia",
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
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // Mostrar éxito
            if (uiState.publishSuccess) {
                SuccessMessage()
            }

            // Información del usuario
            UserInfoSection(
                imgUrl = uiStateProfile.professionals?.imgUrl ?: "",
                name = uiStateProfile.professionals?.name ?: "Usuario",
                profession = uiStateProfile.professionals?.profession ?: "Profesional",
            )

            // Sección de imagen
            ImageUploadSection(
                selectedImageUri = uiState.selectedImageUri,
                onSelectImage = {
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
                onClearImage = { viewModel.clearImage() },
            )

            // Sección de contenido
            NewsContentSection(
                content = uiState.newsContent,
                onContentChange = { viewModel.updateNewsContent(it) },
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botón de publicar
            PublishButton(
                isPublishing = uiState.isPublishing,
                publishSuccess = uiState.publishSuccess,
                onPublish = { viewModel.publishNews() },
                modifier = Modifier.fillMaxWidth(),
                newsContent = uiState.newsContent,
            )
        }
    }
}
