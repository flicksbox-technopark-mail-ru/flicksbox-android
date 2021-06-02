package ru.flicksbox.movie.presentation.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.favourites.FavouritesAdapter
import ru.flicksbox.movie.presentation.favourites.MovieViewData
import ru.flicksbox.movie.presentation.single.MovieClickListener

const val SPAN_COUNT = 2

class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val moviesRecyclerView =
        itemView.findViewById<RecyclerView>(R.id.search_result_recycler)

    fun bind(movies: List<MovieViewData>, movieClickListener: MovieClickListener) {
        val adapter = FavouritesAdapter(movies, movieClickListener)
        moviesRecyclerView.adapter = adapter
        moviesRecyclerView.layoutManager =
            StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
    }
}