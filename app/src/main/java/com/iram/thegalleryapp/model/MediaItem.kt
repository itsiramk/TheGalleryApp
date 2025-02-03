package com.iram.thegalleryapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String,
    val mediaItems: List<MediaItem>,
    val itemCount: Int
) : Parcelable

@Parcelize
data class MediaItem(
    val path: String,
    val name: String,
    val mediaType: Int,
    val dateAdded: Long,
    val isImage: Boolean
) : Parcelable


enum class MediaType { IMAGE, VIDEO }