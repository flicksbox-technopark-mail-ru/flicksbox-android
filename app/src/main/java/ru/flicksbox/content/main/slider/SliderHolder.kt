package ru.flicksbox.content.main.slider

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity


class SliderHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val card: ConstraintLayout = itemView.findViewById(R.id.card_wrapper)

    init {

    }

    fun bind(value: MovieEntity) {

    }
}