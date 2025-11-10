package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID

class StorageRepository {
    private val storage = FirebaseStorage.getInstance()

    suspend fun uploadWorkImage(
        userId: String,
        imageUri: Uri,
    ): String {
        try {
            // Crear nombre único para la imagen
            val imageName = "${UUID.randomUUID()}.jpg"
            val imageRef: StorageReference =
                storage.reference
                    .child("works") // carpeta principal
                    .child(userId) // subcarpeta por usuario
                    .child(imageName)

            // Subir la imagen
            imageRef.putFile(imageUri).await()

            // Obtener la URL de descarga
            return imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception("Error al subir imagen: ${e.message}")
        }
    }

    suspend fun getUserWorkImages(userId: String): List<String> {
        try {
            val userWorksRef = storage.reference.child("works").child(userId)
            val result = userWorksRef.listAll().await()

            val imageUrls = mutableListOf<String>()
            for (item in result.items) {
                val url = item.downloadUrl.await().toString()
                imageUrls.add(url)
            }

            return imageUrls
        } catch (e: Exception) {
            throw Exception("Error al obtener imágenes: ${e.message}")
        }
    }

    suspend fun deleteImage(imageUrl: String) {
        try {
            val storageRef = storage.getReferenceFromUrl(imageUrl)
            storageRef.delete().await()
        } catch (e: Exception) {
            throw Exception("Error al eliminar imagen: ${e.message}")
        }
    }
}
