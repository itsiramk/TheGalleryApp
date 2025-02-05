package com.iram.thegalleryapp

import android.content.Context
import com.iram.thegalleryapp.data.MediaRepository
import com.iram.thegalleryapp.model.Album
import com.iram.thegalleryapp.model.AlbumDetails
import com.iram.thegalleryapp.model.AlbumType
import com.iram.thegalleryapp.presentation.viewmodel.AlbumViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
class AlbumViewModelTest {
    private lateinit var albumViewModel: AlbumViewModel
    private lateinit var mediaRepository: MediaRepository
    private lateinit var context: Context

    @Before
    fun setUp() {
        mediaRepository = mock(MediaRepository::class.java)
        context = mock(Context::class.java)
        albumViewModel = AlbumViewModel(mediaRepository)
        val testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given albums when fetching albums then load current data in StateFlow`() = runTest {
        //Arrange
        val albumDetails = listOf(AlbumDetails("Album 1", "VIDEO ALBUM", AlbumType.VIDEO.ordinal,System.currentTimeMillis(),false),
                            (AlbumDetails("Album 2", "IMAGE ALBUM", AlbumType.IMAGE.ordinal,System.currentTimeMillis(),true)))
        val album = listOf(Album("Device Album",albumDetails,2))
        whenever(mediaRepository.getAlbums(context)).thenReturn(album)

        //Act
        albumViewModel.loadAlbums(context)

        //Assert
        val actualAlbums = albumViewModel.albums.value
        assertEquals(album, actualAlbums)
    }
}
