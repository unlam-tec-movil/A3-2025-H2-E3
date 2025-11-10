package ar.edu.unlam.mobile.scaffolding.ui.screens.professionalProfile.components

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import ar.edu.unlam.mobile.scaffolding.R
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
) {
    val galleryItems =
        remember {
            listOf(
                GalleryItem("Instalación", "de Cañerías", R.drawable.plomeria),
                GalleryItem("Cambio", "de Sistema", R.drawable.gas),
                GalleryItem("Instalación", "de Tablero", R.drawable.tablero),
                GalleryItem("Reparación", "de Muebles", R.drawable.carpintero),
                GalleryItem("Limpieza", "Profunda", R.drawable.limpieza),
                GalleryItem("Afilado", "de Herramientas", R.drawable.work1),
            )
        }

    // Estados simples
    var showCamera by remember { mutableStateOf(false) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val storageRepository = remember { StorageRepository() }

    // Manejar el resultado de la cámara
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture(),
        ) { success ->
            if (success) {
                // La foto fue tomada exitosamente, ahora subirla
                capturedImageUri?.let { uri ->
                    uploadImageToFirebase(uri, storageRepository, context)
                }
                Toast.makeText(context, "La foto se subio exitosamente", Toast.LENGTH_LONG).show()
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

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Primero el botón de agregar
                item {
                    AddGalleryItemCard(
                        onAddClick = {
                            // Verificar permiso antes de abrir la cámara
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                        modifier =
                            Modifier
                                .width(150.dp)
                                .aspectRatio(0.9f),
                    )
                }

                items(galleryItems) { item ->
                    GalleryItemCard(
                        item = item,
                        modifier =
                            Modifier
                                .width(150.dp)
                                .aspectRatio(0.9f),
                        isProfileHV = isProfileHV,
                    )
                }
            }
        } else {
            // Grid 2 columnas para otros perfiles
            val chunkedItems = galleryItems.chunked(2)

            chunkedItems.forEachIndexed { index, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    rowItems.forEach { item ->
                        GalleryItemCard(
                            item = item,
                            modifier = Modifier.weight(1f),
                            isProfileHV = isProfileHV,
                        )
                    }

                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                // Espacio entre filas
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

data class GalleryItem(
    val title: String,
    val subtitle: String,
    val imageRes: Int,
)

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
) {
    // Usar el ID de usuario hardcodeado
    val userId = UserId.ID

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Subir la imagen
            val imageUrl =
                storageRepository.uploadWorkImage(
                    userId = userId,
                    imageUri = imageUri,
                )

            // La imagen se subió exitosamente
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Imagen subida exitosamente", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error al subir imagen: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
