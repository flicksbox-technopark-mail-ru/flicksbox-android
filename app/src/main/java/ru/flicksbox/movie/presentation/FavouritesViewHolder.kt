package ru.flicksbox.movie.presentation

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.movie.domain.TVShowEntity
import ru.flicksbox.utils.buildImageUrl

class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<ImageView>(R.id.film_image)

    fun bind(movie: MovieEntity?, tvShow: TVShowEntity?) {
        movie?.let {
            val path = buildImageUrl(movie.images)
            Picasso.with(itemView.context)
                .load(path)
                .into(imageView)
            imageView.contentDescription = movie.id.toString()
        }

        tvShow?.let {
            val path = buildImageUrl(tvShow.images)
            Picasso.with(itemView.context)
                .load(path)
                .into(imageView)
            imageView.contentDescription = tvShow.id.toString()
        }
    }
}