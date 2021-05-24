package ru.flicksbox.movie.presentation.single

import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.player.PlayClickListener

class OptionsViewHolder(itemView: View, private val playClickListener: PlayClickListener) :
    RecyclerView.ViewHolder(itemView) {

    private val moviePlayButton = itemView.findViewById<ImageButton>(R.id.movie_play_button)

    fun bind() {
        moviePlayButton.setOnClickListener {
            playClickListener.onPlayClick()
        }
    }
}