package ar.edu.unlam.mobile.scaffolding.ui.screens.news

import android.net.Uri

data class CreateNewsUiState(
    val newsContent: String = "",
    val selectedImageUri: Uri? = null,
    val isPublishing: Boolean = false,
    val publishSuccess: Boolean = false,
)
