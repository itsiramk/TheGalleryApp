package com.iram.thegalleryapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iram.thegalleryapp.data.MediaRepository
import com.iram.thegalleryapp.model.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repository: MediaRepository
) : ViewModel() {

    private val _mediaList = MutableLiveData<List<MediaItem>>()
    val mediaList: LiveData<List<MediaItem>> get() = _mediaList

    fun loadMedia(context: Context, albumName: String) {
        viewModelScope.launch(Dispatchers.IO) {
       //     val mediaItems = repository.getMediaFromAlbum(context, albumName)
         //   _mediaList.postValue(mediaItems)
        }
    }
}
