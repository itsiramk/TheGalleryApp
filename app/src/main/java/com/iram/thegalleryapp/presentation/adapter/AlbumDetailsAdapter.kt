package com.iram.thegalleryapp.presentation.adapter

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iram.thegalleryapp.R
import com.iram.thegalleryapp.databinding.ItemImageBinding
import com.iram.thegalleryapp.databinding.ItemVideoBinding
import com.iram.thegalleryapp.model.AlbumDetails
import com.iram.thegalleryapp.model.AlbumType

/**
 * Adapter class for displaying albums on click
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

class AlbumDetailsAdapter(
    private val onVideoClick: (AlbumDetails) -> Unit
) : ListAdapter<AlbumDetails, RecyclerView.ViewHolder>(
    AlbumDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            AlbumType.IMAGE.ordinal -> {
                val binding = ItemImageBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }

            AlbumType.VIDEO.ordinal -> {
                val binding = ItemVideoBinding.inflate(inflater, parent, false)
                VideoViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = getItem(position)
        when (holder) {
            is ImageViewHolder -> holder.bind(album)
            is VideoViewHolder -> holder.bind(album)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val album = getItem(position)
        return if (album.isImage) AlbumType.IMAGE.ordinal else AlbumType.VIDEO.ordinal
    }

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumDetails) {
            binding.albumName.text = album.name
            Glide.with(binding.albumThumbnail.context)
                .load(album.path)
                .placeholder(R.drawable.ic_launcher_background) // Default image if no media found
                .into(binding.albumThumbnail)
        }
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumDetails) {
            binding.albumName.text = album.name
            val thumbnail = getVideoThumbnail(album.path)
            if (thumbnail != null) {
                binding.videoThumbnail.setImageBitmap(thumbnail)
            } else {
                binding.videoThumbnail.setImageResource(android.R.drawable.ic_media_play)
            }
            itemView.setOnClickListener {
                onVideoClick(album)
            }
        }
    }
}

private fun getVideoThumbnail(videoPath: String): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoPath)
        val bitmap = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST)
        retriever.release()
        bitmap
    } catch (e: Exception) {
        null
    }
}

class AlbumDiffCallback : DiffUtil.ItemCallback<AlbumDetails>() {
    override fun areItemsTheSame(oldItem: AlbumDetails, newItem: AlbumDetails): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: AlbumDetails, newItem: AlbumDetails): Boolean =
        oldItem == newItem
}
