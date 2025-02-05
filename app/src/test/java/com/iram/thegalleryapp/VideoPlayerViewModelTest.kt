package com.iram.thegalleryapp

import com.iram.thegalleryapp.presentation.viewmodel.VideoPlayerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class VideoPlayerViewModelTest {

    private lateinit var videoPlayerViewModel: VideoPlayerViewModel

    @Before
    fun setUp() {
        videoPlayerViewModel = VideoPlayerViewModel()
        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test setVideoPath updates videoPath state correctly`() = runTest {
        // Arrange
        val testPath = "path/to/video.mp4"

        // Act
        videoPlayerViewModel.setVideoPath(testPath)

        // Assert
        val actualPath = videoPlayerViewModel.videoPath.value
        assertEquals(testPath, actualPath)
    }
}
