package ru.flicksbox.movie.presentation.player

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import ru.flicksbox.R
import ru.flicksbox.utils.buildVideoUrl


const val MEDIA_URL = "media_url"
const val IS_PLAYING = "is_playing"
const val POSITION = "position"

interface PlayClickListener {
    fun onPlayClick()
}

interface NavigationController {
    fun showNavigation()
    fun hideNavigation()
}

class PlayerFragment : Fragment() {
    private var mediaUrl: String? = null
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mediaUrl = it.getString(MEDIA_URL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as NavigationController).hideNavigation()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        val videoPlayerView = view.findViewById<PlayerView>(R.id.video_player_view)
        player = SimpleExoPlayer.Builder(requireActivity()).build()
        videoPlayerView.player = player
        val source = mediaUrl?.let { buildMediaSource(buildVideoUrl(it)) }
        source?.let { player?.setMediaSource(it) }
        player?.prepare()
        restoreState(savedInstanceState)

        return view
    }

    override fun onDetach() {
        super.onDetach()
        (activity as NavigationController).showNavigation()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.playWhenReady = false
        player?.release()
    }

    private fun buildMediaSource(url: String): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), null)
        val mediaItem = MediaItem.fromUri(url)
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        player?.let { outState.putBoolean(IS_PLAYING, it.isPlaying) }
        player?.let { outState.putLong(POSITION, it.currentPosition) }
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            player?.seekTo(savedInstanceState.getLong(POSITION))
            if (!savedInstanceState.getBoolean(IS_PLAYING))
                player?.pause()
        }
    }

    companion object {
        fun newInstance(mediaUrl: String) =
            PlayerFragment().apply {
                arguments = Bundle().apply {
                    putString(MEDIA_URL, mediaUrl)
                }
            }
    }
}