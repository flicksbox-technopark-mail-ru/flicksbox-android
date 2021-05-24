package ru.flicksbox.movie.presentation.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.single.MovieClickListener

class FavouritesAdapter(
    private var dataSource: List<MovieViewData>,
    private val movieClickListener: MovieClickListener
) : RecyclerView.Adapter<FavouritesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_film, parent, false)
        return FavouritesViewHolder(view, movieClickListener)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun updateData(newDataSource: List<MovieViewData>) {
        dataSource = newDataSource
        notifyDataSetChanged()
    }
}