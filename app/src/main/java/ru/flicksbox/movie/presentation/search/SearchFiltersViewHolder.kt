package ru.flicksbox.movie.presentation.search

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R

interface FilterButtonClickListener {
    fun onRatingButtonClick()
    fun onYearButtonClick()
}

class SearchFiltersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ratingButton = itemView.findViewById<Button>(R.id.search_sort_rating_button)
    private val yearButton = itemView.findViewById<Button>(R.id.search_sort_year_button)

    fun bind(filterButtonClickListener: FilterButtonClickListener) {
        ratingButton.setOnClickListener {
            filterButtonClickListener.onRatingButtonClick()
        }
        yearButton.setOnClickListener {
            filterButtonClickListener.onYearButtonClick()
        }
    }
}