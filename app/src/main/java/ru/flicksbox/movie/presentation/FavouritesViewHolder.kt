package ru.flicksbox.movie.presentation

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.flicksbox.R
import ru.flicksbox.utils.buildImageUrl

data class MovieViewData(
    val images: String,
    val id: Int,
)

class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView = itemView.findViewById<ImageView>(R.id.film_image)

    fun bind(movie: MovieViewData) {
        val path = buildImageUrl(movie.images)
        Picasso.with(itemView.context)
            .load(path)
            .into(imageView)
        imageView.contentDescription = movie.id.toString()
    }
}