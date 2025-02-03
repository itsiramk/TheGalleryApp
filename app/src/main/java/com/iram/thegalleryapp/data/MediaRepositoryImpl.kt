package com.iram.thegalleryapp.data

import android.content.Context
import android.provider.MediaStore
import com.iram.thegalleryapp.model.Album
import com.iram.thegalleryapp.model.MediaItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MediaRepository Implementation for getting albums
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */
@Singleton
class MediaRepositoryImpl @Inject constructor() : MediaRepository {
    override suspend fun getAlbums(context: Context): List<Album> {
        val albumMap = mutableMapOf<String, MutableList<MediaItem>>()
        val allImages = mutableListOf<MediaItem>()
        val allVideos = mutableListOf<MediaItem>()
        val cameraAlbum = mutableListOf<MediaItem>()

        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.MEDIA_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} IN (?,?)"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val sortOrder = "${MediaStore.MediaColumns.DATE_ADDED} DESC"

        val uri = MediaStore.Files.getContentUri("external")
        context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
            ?.use { cursor ->
                val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                val bucketColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
                val mediaTypeColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE)

                while (cursor.moveToNext()) {
                    val path = cursor.getString(pathColumn)
                    val name = cursor.getString(nameColumn) ?: "Unknown"
                    val folder = cursor.getString(bucketColumn) ?: "Unknown"
                    val mediaType = cursor.getInt(mediaTypeColumn)

                    // Filter out cache, thumbnails, and hidden files
                    if (path.contains("/cache/") || path.contains(".nomedia")) continue

                    val mediaItem = MediaItem(
                        path,
                        name,
                        mediaType,
                        System.currentTimeMillis(),
                        mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    )

                    // Categorize media into respective albums
                    albumMap.getOrPut(folder) { mutableListOf() }.add(mediaItem)

                    // Add to "All Images" or "All Videos"
                    if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                        allImages.add(mediaItem)
                        if (folder.equals("Camera", ignoreCase = true)) {
                            cameraAlbum.add(mediaItem)
                        }
                    } else if (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                        allVideos.add(mediaItem)
                    }
                }
            }

        // Convert map to album list
        val albums = albumMap.map { Album(it.key, it.value, it.value.size) }.toMutableList()

        // Add special folders
        if (allImages.isNotEmpty()) albums.add(0, Album("All Images", allImages, allImages.size))
        if (allVideos.isNotEmpty()) albums.add(1, Album("All Videos", allVideos, allVideos.size))
        if (cameraAlbum.isNotEmpty()) albums.add(2, Album("Camera", cameraAlbum, cameraAlbum.size))

        return albums
    }
}
