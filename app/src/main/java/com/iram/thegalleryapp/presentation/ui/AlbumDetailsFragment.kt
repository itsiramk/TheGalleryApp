package com.iram.thegalleryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.iram.thegalleryapp.databinding.FragmentAlbumDetailsBinding
import com.iram.thegalleryapp.model.AlbumDetails
import com.iram.thegalleryapp.presentation.adapter.AlbumDetailsAdapter
import com.iram.thegalleryapp.presentation.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Class for displaying album details
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 4th Feb 2025
 */

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
        val args: AlbumDetailsFragmentArgs by navArgs()
        val albumArray = args.albumDetails
        val albumList = albumArray.map { it as AlbumDetails }

        albumList.let {
            albumDetailsAdapter = AlbumDetailsAdapter(
                onVideoClick = { mediaItem ->
                val viewModel = ViewModelProvider(this)[VideoPlayerViewModel::class.java]
                viewModel.setVideoPath(mediaItem.path)
                VideoPlayerViewModel().setVideoPath(mediaItem.path)
                val fragment = VideoPlayerDialogFragment.newInstance(mediaItem.path)
                fragment.show(childFragmentManager, "videoPlayer")
            })
            binding.recyclerViewMedia.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recyclerViewMedia.adapter = albumDetailsAdapter
            albumDetailsAdapter.submitList(it)
        }
    }
}
