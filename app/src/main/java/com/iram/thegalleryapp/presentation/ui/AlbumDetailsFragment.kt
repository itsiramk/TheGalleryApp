package com.iram.thegalleryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.iram.thegalleryapp.presentation.adapter.AlbumDetailsAdapter
import com.iram.thegalleryapp.databinding.FragmentAlbumDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment : Fragment() {

    private lateinit var albumDetailsAdapter: AlbumDetailsAdapter
    private lateinit var binding: FragmentAlbumDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val album = AlbumDetailsFragmentArgs.fromBundle(requireArguments()).album
        album.let {
            binding.album = it
            albumDetailsAdapter = AlbumDetailsAdapter()
            binding.recyclerViewMedia.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.recyclerViewMedia.adapter = albumDetailsAdapter
            albumDetailsAdapter.submitList(it.mediaItems)
        }
    }
}
