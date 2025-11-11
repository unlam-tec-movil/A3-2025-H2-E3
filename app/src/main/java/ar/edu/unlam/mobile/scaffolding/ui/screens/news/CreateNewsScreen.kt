package ar.edu.unlam.mobile.scaffolding.ui.screens.news

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.createnews.CreateNewsViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.profile.ProfileViewModel
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun CreateNewsScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateNewsViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiStateProfile by profileViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Launcher para seleccionar imagen de la galería
    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                viewModel.updateSelectedImage(uri)
            },
        )

    // Resetear estado de éxito después de un tiempo
    if (uiState.publishSuccess) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(3000)
            viewModel.clearPublishStatus()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
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
            // Información del usuario
            UserInfoSection(
                uiStateProfile.professionals?.imgUrl.toString(),
                uiStateProfile.professionals?.name.toString(),
                uiStateProfile.professionals?.profession.toString(),
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
            )
        }
    }
}

@Composable
private fun UserInfoSection(
    imgUrl: String = "",
    name: String = "",
    profession: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentHeight(),
    ) {
        // Avatar
        Box(
            modifier =
                Modifier
                    .size(60.dp)
                    .background(Color.Gray, CircleShape)
                    .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = imgUrl,
                contentDescription = "imagen",
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Información del profesional
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = profession,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Composable
private fun ImageUploadSection(
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit,
    onClearImage: () -> Unit,
) {
    Column {
        Text(
            text = "Sube una imagen",
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 16.sp,
        )

        Text(
            text = "Presenta tu último trabajo o una oferta especial",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 16.sp,
        )

        if (selectedImageUri != null) {
            // Mostrar imagen seleccionada
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
            ) {
                Image(
                    painter =
                        rememberAsyncImagePainter(
                            ImageRequest
                                .Builder(LocalContext.current)
                                .data(selectedImageUri)
                                .build(),
                        ),
                    contentDescription = "Seleccionar un imagen",
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )

                // Botón para eliminar imagen
                IconButton(
                    onClick = onClearImage,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove image",
                        tint = Color.White,
                    )
                }
            }
        } else {
            // Botón para seleccionar imagen
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        .clickable(onClick = onSelectImage),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Select image",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(32.dp),
                    )
                    Text(
                        text = "Seleccionar una imagen",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun NewsContentSection(
    content: String,
    onContentChange: (String) -> Unit,
) {
    Column {
        Text(
            text = "Escribe tu noticia",
            style =
                MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 16.sp,
        )

        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            placeholder = {
                Text(
                    text = "Comparte detalles sobre tu trabajo, consejos o promociones...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
            singleLine = false,
            maxLines = 10,
        )
    }
}

@Composable
private fun PublishButton(
    isPublishing: Boolean,
    publishSuccess: Boolean,
    onPublish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = onPublish,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                ),
            enabled = !isPublishing && !publishSuccess,
        ) {
            if (isPublishing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp,
                )
            } else if (publishSuccess) {
                Text(
                    text = "Published!",
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                        ),
                )
            } else {
                Text(
                    text = "Publish News",
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
