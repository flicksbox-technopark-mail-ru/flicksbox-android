package ru.flicksbox.content.main.slider

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity


class SliderHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val card: ConstraintLayout = itemView.findViewById(R.id.card_wrapper)
    private val contentTitleRu: TextView = card.findViewById(R.id.content_title_ru)
    private val cardPoster: ImageView = card.findViewById(R.id.card_poster)

    fun bind(value: MovieEntity) {
        Log.e("LNP", "LNP " + value.images);
        this.contentTitleRu.text = value.name

        this.cardPoster.setImageURI(Uri.parse("http://www.flicksbox.ru" + value.images + "/640")) //TODO
    }
}