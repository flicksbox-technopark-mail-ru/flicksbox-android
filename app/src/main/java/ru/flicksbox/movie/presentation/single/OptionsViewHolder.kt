package ru.flicksbox.movie.presentation.single

import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.player.PlayClickListener

class OptionsViewHolder(
    itemView: View,
    private val playClickListener: PlayClickListener,
    private val favouritesClickListener: FavouritesClickListener,
) :
    RecyclerView.ViewHolder(itemView) {

    private val moviePlayButton = itemView.findViewById<ImageButton>(R.id.movie_play_button)
    private val favouritesButton = itemView.findViewById<ImageButton>(R.id.movie_favorites_button)

    private val addedResID = R.drawable.ic_is_added
    private val notAddedResID = R.drawable.ic_add
    private var resID = notAddedResID

    fun bind(movieID: Int, isFavourite: Boolean) {
        moviePlayButton.setOnClickListener {
            playClickListener.onPlayClick()
        }

        if (isFavourite) {
            favouritesButton.setImageResource(addedResID)
            resID = addedResID
        }

        favouritesButton.setOnClickListener {
            if (resID == addedResID) {
                favouritesClickListener.onDeleteFromFavouritesClick(movieID,
                    { onDeleteSuccess() },
                    { onDeleteError() })
            } else {
                favouritesClickListener.onAddToFavouritesClick(movieID,
                    { onAddSuccess() },
                    { onAddError() })
            }
        }
    }

    private fun onAddSuccess() {
        favouritesButton.setImageResource(addedResID)
    }

    private fun onAddError() {
        favouritesButton.setImageResource(notAddedResID)
    }

    private fun onDeleteSuccess() {
        favouritesButton.setImageResource(notAddedResID)
    }

    private fun onDeleteError() {
        favouritesButton.setImageResource(addedResID)
    }
}