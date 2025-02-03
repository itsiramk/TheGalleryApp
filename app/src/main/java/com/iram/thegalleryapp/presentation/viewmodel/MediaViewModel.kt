package com.iram.thegalleryapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iram.thegalleryapp.data.MediaRepository
import com.iram.thegalleryapp.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(private val mediaRepository: MediaRepository) :
    ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> get() = _albums

    fun loadAlbums(context: Context) {
        viewModelScope.launch {
            _albums.value = mediaRepository.getAlbums(context)
            android.util.Log.d("Iram", "loadAlbums: ${_albums.value.size}")
        }
    }
}
