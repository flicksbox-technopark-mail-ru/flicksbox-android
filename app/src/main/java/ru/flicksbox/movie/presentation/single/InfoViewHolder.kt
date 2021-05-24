package ru.flicksbox.movie.presentation.single

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R

class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val descriptionView = itemView.findViewById<TextView>(R.id.single_movie_description)
    private val countryView = itemView.findViewById<TextView>(R.id.single_movie_country)
    private val genreView = itemView.findViewById<TextView>(R.id.single_movie_genre)
    private val actorsView = itemView.findViewById<TextView>(R.id.single_movie_actors)

    fun bind(movieInfo: InfoViewData) {
        descriptionView.text = movieInfo.description
        countryView.text = movieInfo.country.joinToString()
        genreView.text = movieInfo.genre.joinToString()
        actorsView.text = movieInfo.actors.joinToString()
    }
}

data class InfoViewData(
    val description: String,
    val country: List<String>,
    val genre: List<String>,
    val actors: List<String>
)