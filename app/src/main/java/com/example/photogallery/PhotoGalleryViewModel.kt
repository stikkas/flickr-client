package com.example.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.model.GallaryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository: PhotoRepository = PhotoRepository()
    private val preferencesRepository = PreferencesRepository.get()
    private val _uiState: MutableStateFlow<PhotoGalleryUiState> = MutableStateFlow(PhotoGalleryUiState())
    val uiState: StateFlow<PhotoGalleryUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest {q ->
                try {
                    val items = fetchGalleryItems(q)
                    _uiState.update {
                        it.copy(images = items, query = q)
                    }
                } catch (ex: Exception) {
                    Log.e(TAG, "Failed to fetch gallery items", ex)
                }
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch {
            preferencesRepository.setStoredQuery(query)
//            _galleryItems.value =
//            try {
//                fetchGalleryItems(query)
//            } catch (ex: Exception) {
//                Log.e(TAG, "error: ${ex.message}")
//                emptyList()
//            }
        }
    }

    private suspend fun fetchGalleryItems(query: String): List<GallaryItem> {
        return if (query.isNotEmpty()) {
            photoRepository.searchPhotos(query)
        } else {
            photoRepository.fetchPhotos()
        }
    }
}

data class PhotoGalleryUiState(
    val images: List<GallaryItem> = listOf(),
    val query: String = ""
)