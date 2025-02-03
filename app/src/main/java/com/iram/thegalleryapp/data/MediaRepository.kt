package com.iram.thegalleryapp.data

import android.content.Context
import com.iram.thegalleryapp.model.Album

/**
 * MediaRepository for getting albums
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */
interface MediaRepository {
    suspend fun getAlbums(context: Context): List<Album>
}
