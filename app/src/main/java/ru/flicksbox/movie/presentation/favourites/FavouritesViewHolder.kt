package ru.flicksbox.movie.presentation.favourites

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.single.MovieClickListener
import ru.flicksbox.utils.buildImageUrl

data class MovieViewData(
    val images: String,
    val id: Int,
    val rating: String,
    val year: Int,
)

class FavouritesViewHolder(itemView: View,
                           private val movieClickListener: MovieClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<ImageView>(R.id.film_image)

    fun bind(movie: MovieViewData) {
        val path = buildImageUrl(movie.images)
        Glide.with(itemView)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
        imageView.contentDescription = movie.id.toString()
        imageView.setOnClickListener {
            movieClickListener.onMovieClick(movie.id)
        }
    }
}