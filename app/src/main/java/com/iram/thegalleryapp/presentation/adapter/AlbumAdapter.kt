package com.iram.thegalleryapp.presentation.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iram.thegalleryapp.R
import com.iram.thegalleryapp.databinding.ItemAlbumBinding
import com.iram.thegalleryapp.model.Album

class AlbumAdapter(private val onAlbumClick: (Album) -> Unit) :
    ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)
    }

    inner class AlbumViewHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.albumName.text = album.name
            binding.albumCount.text = TextUtils.concat("${album.mediaItems.size}, items")
            if (album.mediaItems.isNotEmpty()) {
                val firstMediaPath = album.mediaItems[0].path //Loads first media item as thumbnail
                Glide.with(binding.albumThumbnail.context)
                    .load(firstMediaPath)
                    .placeholder(R.drawable.ic_launcher_background) // Default image if no media found
                    .into(binding.albumThumbnail)
            } else {
                binding.albumThumbnail.setImageResource(R.drawable.ic_launcher_background)
            }

            itemView.setOnClickListener { onAlbumClick(album) }
        }
    }

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem == newItem
    }
}
