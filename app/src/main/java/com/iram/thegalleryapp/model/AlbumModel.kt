package com.iram.thegalleryapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Model class for Album
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

@Parcelize
data class Album(
    val name: String,
    val albumDetails: List<AlbumDetails>,
    val itemCount: Int
) : Parcelable

@Parcelize
data class AlbumDetails(
    val path: String,
    val name: String,
    val mediaType: Int,
    val dateAdded: Long,
    val isImage: Boolean
) : Parcelable


enum class AlbumType { IMAGE, VIDEO }