package ru.flicksbox.movie.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data

const val SPAN_COUNT = 2

class FavouritesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.favourites_recycler)
        recycler.layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)

        // TODO: showLoadingBar()

        val favouritesData = runBlocking {
            App.movieInteractor.getUserMovies().single()
        }

        when (favouritesData) {
            is Data.Content -> {
                // TODO: stopLoadingBar()
                recycler.adapter = FavouritesAdapter(favouritesData.content)
            }
        }

        return view
    }
}