package ru.flicksbox.content.main.slider

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

class TopSliderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.slider_recyclerview, container, false)
        val topRecycler = view.findViewById<RecyclerView>(R.id.recycler_slider)
        topRecycler.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val topData = listOf<MovieEntity>()
        val topAdapter = SliderAdapter(topData)
        topRecycler.adapter = topAdapter

        App.movieInteractor.getTopMovies(15, 0)
            .flowOn(Dispatchers.IO)
            .onEach { movies ->
                when (movies) {
                    is Data.Error -> Log.d("HERE", movies.toString())
                    is Data.Loading -> Log.d("HERE", movies.toString())
                    is Data.Content -> {
                        activity?.runOnUiThread {
                            topAdapter.updateData(movies.content)
                        }
                    }
                }
            }
            .launchIn(GlobalScope)

        return view
    }
}