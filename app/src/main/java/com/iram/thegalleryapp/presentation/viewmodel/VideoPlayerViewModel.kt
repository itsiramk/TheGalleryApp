package com.iram.thegalleryapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Viewmodel for VideoPlayer
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 4th Feb 2025
 */

class VideoPlayerViewModel : ViewModel() {

    private val _videoPath = MutableStateFlow<String?>(null)
    val videoPath: StateFlow<String?> get() = _videoPath

    fun setVideoPath(path: String) {
        _videoPath.value = path
    }
}
