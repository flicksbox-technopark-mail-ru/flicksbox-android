package ru.flicksbox.movie.presentation.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.FavouritesEntity
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.movie.domain.TVShowEntity
import ru.flicksbox.movie.presentation.single.MovieClickListener
import ru.flicksbox.movie.presentation.single.SingleMovieFragment
import ru.flicksbox.utils.notifyError

const val SPAN_COUNT = 2

class FavouritesFragment : Fragment(), MovieClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.favourites_recycler)
        recycler.layoutManager =
            StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
        val adapter = FavouritesAdapter(emptyList(), this)
        recycler.adapter = adapter

        App.movieInteractor.getUserMovies()
            .flowOn(Dispatchers.IO)
            .onEach { favouritesData ->
                when (favouritesData) {
                    is Data.Content -> {
                        adapter.updateData(favouritesData.content.toViewData())
                    }

                    is Data.Loading -> {

                    }

                    is Data.Error -> {
                        activity?.runOnUiThread {
                            notifyError(favouritesData.throwable, requireContext())
                        }
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))

        return view
    }

    override fun onMovieClick(movieID: Int) {
        val fm = activity?.supportFragmentManager?.beginTransaction() ?: return
        val fragment = SingleMovieFragment.newInstance(movieID)
        fm.replace(R.id.favourites_layout, fragment)
        fm.addToBackStack(null).commit()
    }
}

fun FavouritesEntity.toViewData(): List<MovieViewData> {
    val result = mutableListOf<MovieViewData>()
    for (movie: MovieEntity in this.movies) {
        result.add(MovieViewData(movie.images, movie.id, movie.rating, movie.year))
    }
    for (tvShow: TVShowEntity in this.tvShows) {
        result.add(MovieViewData(tvShow.images, tvShow.id, tvShow.rating, tvShow.year))
    }
    return result
}