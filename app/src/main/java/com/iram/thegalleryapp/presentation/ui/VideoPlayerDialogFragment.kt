package com.iram.thegalleryapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.iram.thegalleryapp.databinding.LayoutDialogVideoBinding
import com.iram.thegalleryapp.presentation.viewmodel.VideoPlayerViewModel

/**
 * VideoPlayerDialogFragment for viewing videos
 * Created by: Iram Khan
 * Email: khan.iram02@gmail.com
 * Date: 4th Feb 2025
 */

class VideoPlayerDialogFragment : DialogFragment() {

    private lateinit var viewModel: VideoPlayerViewModel
    private var exoPlayer: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = LayoutDialogVideoBinding.inflate(inflater, container, false)
        playerView = binding.playerView
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        playerView.player = exoPlayer
        viewModel = ViewModelProvider(this)[VideoPlayerViewModel::class.java]
        arguments?.getString("VIDEO_PATH")?.let {
            viewModel.setVideoPath(it)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.videoPath.collect { path ->
                if (!path.isNullOrEmpty()) {
                    val mediaItem = MediaItem.fromUri(path)
                    exoPlayer?.setMediaItem(mediaItem)
                    exoPlayer?.prepare()
                    exoPlayer?.play()
                }
            }
        }
        binding.closeButton.setOnClickListener {
            exoPlayer?.release()
            dismiss()
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer?.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
    }

    companion object {
        fun newInstance(videoPath: String): VideoPlayerDialogFragment {
            val fragment = VideoPlayerDialogFragment()
            val args = Bundle()
            args.putString("VIDEO_PATH", videoPath)
            fragment.arguments = args
            return fragment
        }
    }
}
