package ru.flicksbox.movie.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.movie.presentation.favourites.MovieViewData
import ru.flicksbox.movie.presentation.single.MovieClickListener
import ru.flicksbox.movie.presentation.single.SingleMovieFragment

class MainFragment : Fragment(), MovieClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val latestRecycler = view.findViewById<RecyclerView>(R.id.slider_content_latest_wrapper)
        latestRecycler.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val latestData = listOf<MovieViewData>()
        val latestAdapter = SliderAdapter(latestData, this)
        latestRecycler.adapter = latestAdapter

        App.movieInteractor.getLatestMovies(20, 0)
            .flowOn(Dispatchers.IO)
            .onEach { movies ->
                when (movies) {
                    is Data.Error -> Log.d("HERE", movies.toString())
                    is Data.Loading -> Log.d("HERE", movies.toString())
                    is Data.Content -> {
                        activity?.runOnUiThread {
                            latestAdapter.updateData(movies.content.map { it.toViewData() })
                        }
                    }
                }
            }
            .launchIn(GlobalScope)

        val topRecycler = view.findViewById<RecyclerView>(R.id.slider_content_top_wrapper)
        topRecycler.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val topData = listOf<MovieViewData>()
        val topAdapter = SliderAdapter(topData, this)
        topRecycler.adapter = topAdapter

        App.movieInteractor.getTopMovies(20, 0)
            .flowOn(Dispatchers.IO)
            .onEach { movies ->
                when (movies) {
                    is Data.Error -> Log.d("HERE", movies.toString())
                    is Data.Loading -> Log.d("HERE", movies.toString())
                    is Data.Content -> {
                        activity?.runOnUiThread {
                            topAdapter.updateData(movies.content.map { it.toViewData() })
                        }
                    }
                }
            }
            .launchIn(GlobalScope)

        return view
    }

    override fun onMovieClick(movieID: Int) {
        val fm = activity?.supportFragmentManager?.beginTransaction() ?: return
        val fragment = SingleMovieFragment.newInstance(movieID)
        fm.replace(R.id.main_layout, fragment)
        fm.addToBackStack(null).commit()
    }
}

fun MovieEntity.toViewData(): MovieViewData =
    MovieViewData(this.images, this.id)
