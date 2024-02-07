package com.example.photogallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photogallery.model.GallaryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryViewModel"

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository: PhotoRepository = PhotoRepository()
    private val _galleryItems: MutableStateFlow<List<GallaryItem>> = MutableStateFlow(emptyList())
    private val preferencesRepository = PreferencesRepository.get()
    val galleryItems: StateFlow<List<GallaryItem>>
        get() = _galleryItems.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.storedQuery.collectLatest {
                try {
                    val items = fetchGalleryItems(it)
                    _galleryItems.value = items
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