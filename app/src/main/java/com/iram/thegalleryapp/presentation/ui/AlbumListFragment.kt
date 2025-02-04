package com.iram.thegalleryapp.presentation.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.iram.thegalleryapp.databinding.FragmentAlbumListBinding
import com.iram.thegalleryapp.presentation.adapter.AlbumAdapter
import com.iram.thegalleryapp.presentation.adapter.GridDividerItemDecoration
import com.iram.thegalleryapp.presentation.viewmodel.AlbumViewModel
import com.iram.thegalleryapp.utils.AppConstant
import com.iram.thegalleryapp.utils.AppConstant.GRID_BORDER
import com.iram.thegalleryapp.utils.AppConstant.SPAN_COUNT
import com.iram.thegalleryapp.utils.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Class for displaying album of the device
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 3rd Feb 2025
 */

@AndroidEntryPoint
class AlbumListFragment : Fragment() {
    private lateinit var binding: FragmentAlbumListBinding
    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        setAlbumData()
        setUpRecyclerView()
        return binding.root
    }

    private fun setAlbumData() {
        albumAdapter = AlbumAdapter(
            onAlbumClick = { album ->
                val action = AlbumListFragmentDirections.actionHomeToAlbumDetails(
                    album.name,
                    album.albumDetails.toTypedArray()
                )
                findNavController().navigate(action)
            }
        )
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
            adapter = albumAdapter
            addItemDecoration(GridDividerItemDecoration(SPAN_COUNT, GRID_BORDER, Color.BLACK))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumTitle.text = AppConstant.APP_TITLE
        requestStoragePermissions()
        loadAlbums()
    }

    private fun loadAlbums() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albums.collect { albumList ->
                    albumAdapter.submitList(albumList)
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
            val isGranted = PermissionUtil.isStoragePermissionGranted(requireContext())

            if (isGranted) {
                viewModel.loadAlbums(requireContext())
            } else {
                handlePermissionDenied()
            }
        }

    private fun handlePermissionDenied() {
        val activity = requireActivity()
        if (PermissionUtil.shouldShowPermissionRationale(activity)) {
            PermissionUtil.showPermissionRationaleDialog(requireContext()) {
                requestStoragePermissions()
            }
        } else {
            PermissionUtil.showSettingsDialog(requireContext())
        }
    }

    private fun requestStoragePermissions() {
        if (PermissionUtil.isStoragePermissionGranted(requireContext())) {
            viewModel.loadAlbums(requireContext())
        } else {
            PermissionUtil.requestStoragePermissions(requestPermissionLauncher)
        }
    }
}
