package ru.flicksbox.content.main.slider

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.utils.buildImageUrl


class SliderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val card: ConstraintLayout = itemView.findViewById(R.id.card_wrapper)
    private val contentTitleRu: TextView = card.findViewById(R.id.content_title_ru)
    private val cardPoster: ImageView = card.findViewById(R.id.card_poster)

    fun bind(value: MovieEntity) {
        val path = buildImageUrl(value.images)
        Picasso
            .with(itemView.context)
            .load(path)
            .into(cardPoster)
    }
}