package ru.flicksbox.movie.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.FavouritesEntity

class FavouritesAdapter(private val dataSource: FavouritesEntity) : RecyclerView.Adapter<FavouritesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_component, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        if (position < dataSource.movies.size)
            holder.bind(dataSource.movies[position], null)
        else if (position - dataSource.movies.size < dataSource.tvShows.size && position - dataSource.movies.size >= 0 )
            holder.bind(null, dataSource.tvShows[position - dataSource.movies.size])
    }

    override fun getItemCount(): Int {
        return dataSource.movies.size + dataSource.tvShows.size
    }
}