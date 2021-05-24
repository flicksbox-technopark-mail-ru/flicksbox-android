package ru.flicksbox.movie.presentation.main

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.favourites.MovieViewData
import ru.flicksbox.movie.presentation.single.MovieClickListener
import ru.flicksbox.utils.buildImageUrl

class SliderHolder(itemView: View, private val listener: MovieClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val card: ConstraintLayout = itemView.findViewById(R.id.card_wrapper)
    private val cardPoster: ImageView = card.findViewById(R.id.card_poster)

    fun bind(value: MovieViewData) {
        val path = buildImageUrl(value.images)
        Picasso
            .with(itemView.context)
            .load(path)
            .into(cardPoster)
        cardPoster.contentDescription = value.id.toString()

        cardPoster.setOnClickListener {
            listener.onMovieClick(value.id)
        }
    }
}