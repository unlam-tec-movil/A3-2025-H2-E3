package ar.edu.unlam.mobile.scaffolding.ui.screens.createnews

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.ui.screens.news.CreateNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateNewsViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(CreateNewsUiState())
        val uiState: StateFlow<CreateNewsUiState> = _uiState.asStateFlow()

        fun updateNewsContent(content: String) {
            _uiState.update { it.copy(newsContent = content) }
        }

        fun updateSelectedImage(uri: Uri?) {
            _uiState.update { it.copy(selectedImageUri = uri) }
        }

        fun clearImage() {
            _uiState.update { it.copy(selectedImageUri = null) }
        }

        fun publishNews() {
            _uiState.update { it.copy(isPublishing = true) }

            // Simular publicación
            viewModelScope.launch {
                kotlinx.coroutines.delay(1500)
                _uiState.update {
                    it.copy(
                        isPublishing = false,
                        publishSuccess = true,
                    )
                }
            }
        }

        fun clearPublishStatus() {
            _uiState.update { it.copy(publishSuccess = false) }
        }
    }
