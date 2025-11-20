package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components.galery

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import ar.edu.unlam.mobile.scaffolding.data.repositories.StorageRepository
import ar.edu.unlam.mobile.scaffolding.ui.components.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun GallerySection(
    modifier: Modifier = Modifier,
    isProfileHV: Boolean = false,
    userIdGalery: String = "",
    onImageClick: (String) -> Unit = {}, // Nuevo parámetro para manejar el click
) {
    val context = LocalContext.current
    val storageRepository = remember { StorageRepository() }

    // Estado para las URLs de las imágenes
    var galleryUrls by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Estados para la cámara
    var showCamera by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Función para cargar imágenes desde Firebase
    val loadImages =
        remember {
            {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val userId = if (userIdGalery.isNotEmpty()) userIdGalery else UserId.ID
                        val images = storageRepository.getUserWorkImages(userId)
                        withContext(Dispatchers.Main) {
                            galleryUrls = images
                            isLoading = false
                            errorMessage = null
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            errorMessage = "Error al cargar imágenes: ${e.message}"
                            isLoading = false
                            // En caso de error, mostrar imágenes de ejemplo
                            galleryUrls = getFallbackImages()
                        }
                    }
                }
            }
        }

    // Cargar imágenes al iniciar el composable
    LaunchedEffect(Unit) {
        loadImages()
    }

    // Manejar el resultado de la cámara
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
        ) { success ->
            if (success) {
                capturedImageUri?.let { uri ->
                    uploadImageToFirebase(uri, storageRepository, context) {
                        // Recargar todas las imágenes después de subir una nueva
                        loadImages()
                    }
                }
                Toast.makeText(context, "La foto se subió exitosamente", Toast.LENGTH_LONG).show()
            }
            showCamera = false
        }

    // Manejar permisos de la cámara
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                showCamera = true
            } else {
                Toast.makeText(context, "Se necesita permiso de cámara para tomar fotos", Toast.LENGTH_LONG).show()
            }
        }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        if (isProfileHV) {
            // Título de la sección
            Text(
                text = if (isProfileHV) "Mis Trabajos" else "",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
            )

            // Mostrar estado de carga
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Cargando imágenes...")
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Mostrar error si existe
            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Primero el botón de agregar (solo en perfil propio)
                if (isProfileHV) {
                    item {
                        AddGalleryItemCard(
                            onAddClick = {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            },
                            modifier =
                                Modifier
                                    .width(150.dp)
                                    .aspectRatio(0.9f),
                        )
                    }
                }

                // Mostrar las imágenes de Firebase
                items(galleryUrls) { imageUrl ->
                    GalleryItemCard(
                        imageUrl = imageUrl,
                        modifier =
                            Modifier
                                .width(150.dp)
                                .aspectRatio(0.9f),
                        isProfileHV = isProfileHV,
                        onDeleteClick =
                            if (isProfileHV) {
                                {
                                    // Función para eliminar imagen
                                    deleteImageFromFirebase(imageUrl, storageRepository, context) {
                                        // Recargar todas las imágenes después de eliminar
                                        loadImages()
                                    }
                                }
                            } else {
                                null
                            },
                        onImageClick = {
                            onImageClick(imageUrl) // Pasar el click al padre
                        },
                    )
                }
            }
        } else {
            // Grid 2 columnas para otros perfiles
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Cargando imágenes...")
                }
            }

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }

            val chunkedItems = galleryUrls.chunked(2)

            chunkedItems.forEachIndexed { index, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    rowItems.forEach { item ->
                        GalleryItemCard(
                            imageUrl = item,
                            modifier = Modifier.weight(1f),
                            isProfileHV = isProfileHV,
                            onDeleteClick = null,
                            onImageClick = {
                                onImageClick(item) // Pasar el click al padre
                            },
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                if (index < chunkedItems.size - 1) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }

    // Lógica para abrir la cámara
    if (showCamera) {
        OpenCamera(
            cameraLauncher = cameraLauncher,
            onImageCaptured = { uri ->
                capturedImageUri = uri
            },
        )
    }
}

@Composable
fun OpenCamera(
    cameraLauncher: ActivityResultLauncher<Uri>,
    onImageCaptured: (Uri) -> Unit,
) {
    val context = LocalContext.current

    val imageUri =
        remember {
            try {
                // Crear archivo temporal
                val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val file =
                    File.createTempFile(
                        "IMG_${System.currentTimeMillis()}",
                        ".jpg",
                        storageDir,
                    )

                // Obtener URI usando FileProvider
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file,
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    LaunchedEffect(Unit) {
        if (imageUri != null) {
            cameraLauncher.launch(imageUri)
            onImageCaptured(imageUri)
        }
    }
}

// Función para subir la imagen a Firebase
private fun uploadImageToFirebase(
    imageUri: Uri,
    storageRepository: StorageRepository,
    context: Context,
    onSuccess: () -> Unit = {},
) {
    val userId = UserId.ID

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Subir la imagen
            storageRepository.uploadWorkImage(
                userId = userId,
                imageUri = imageUri,
            )

            // Notificar éxito
            withContext(Dispatchers.Main) {
                onSuccess()
                Toast.makeText(context, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al subir imagen: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

// Función para eliminar imagen de Firebase
private fun deleteImageFromFirebase(
    imageUrl: String,
    storageRepository: StorageRepository,
    context: Context,
    onSuccess: () -> Unit = {},
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            storageRepository.deleteImage(imageUrl)
            withContext(Dispatchers.Main) {
                onSuccess()
                Toast.makeText(context, "Imagen eliminada", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al eliminar imagen: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

// Función auxiliar para obtener imágenes de fallback
private fun getFallbackImages(): List<String> =
    listOf(
        "https://upload.wikimedia.org/wikipedia/commons/c/c7/Loading_2.gif",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlDj5uzxqgO1KC5msT8g9SNZmHOnyOC0yTvw&s",
    )
