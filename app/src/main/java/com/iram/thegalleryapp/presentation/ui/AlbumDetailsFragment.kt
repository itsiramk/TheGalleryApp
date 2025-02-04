package com.iram.thegalleryapp.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.iram.thegalleryapp.databinding.FragmentAlbumDetailsBinding
import com.iram.thegalleryapp.model.AlbumDetails
import com.iram.thegalleryapp.presentation.adapter.AlbumDetailsAdapter
import com.iram.thegalleryapp.presentation.adapter.GridDividerItemDecoration
import com.iram.thegalleryapp.presentation.viewmodel.VideoPlayerViewModel
import com.iram.thegalleryapp.utils.AppConstant.GRID_BORDER
import com.iram.thegalleryapp.utils.AppConstant.SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        showProgressBar()
        setupRecyclerView()
        val args: AlbumDetailsFragmentArgs by navArgs()
        loadAlbumDetails(args.albumDetails)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        albumDetailsAdapter = AlbumDetailsAdapter(
            onVideoClick = { mediaItem -> openVideoPlayer(mediaItem.path) }
        )
        with(binding.recyclerViewMedia){
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            addItemDecoration(GridDividerItemDecoration(SPAN_COUNT, GRID_BORDER, Color.BLACK))
            adapter = albumDetailsAdapter
        }
    }

    private fun loadAlbumDetails(albumArray: Array<Parcelable>) {
        if (albumArray.isEmpty()) {
            hideProgressBar()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val albumList = withContext(Dispatchers.Default) {
                albumArray.map { it as AlbumDetails }
            }
            albumDetailsAdapter.submitList(albumList) {
                hideProgressBar()
            }
        }
    }

    private fun openVideoPlayer(videoPath: String) {
        val viewModel = ViewModelProvider(this)[VideoPlayerViewModel::class.java]
        viewModel.setVideoPath(videoPath)

        val fragment = VideoPlayerDialogFragment.newInstance(videoPath)
        fragment.show(childFragmentManager, this@AlbumDetailsFragment.javaClass.simpleName)
    }

}
