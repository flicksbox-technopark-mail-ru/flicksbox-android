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


const val CACHE_SIZE = 10

class SliderFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.slider_recyclerview, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_slider)
        recycler.setItemViewCacheSize(CACHE_SIZE)
        recycler.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        val data = listOf<MovieEntity>()
        val adapter = SliderAdapter(data)
        recycler.adapter = adapter

        activity?.runOnUiThread(java.lang.Runnable {
            App.movieInteractor.getTopMovies(15, 0)
                .flowOn(Dispatchers.IO)
                .onEach { movies ->
                    when (movies) {
                        is Data.Error -> Log.d("HERE", movies.toString())
                        is Data.Loading -> Log.d("HERE", movies.toString())
                        is Data.Content -> {
                            adapter.updateData(movies.content)
                            Log.d("HERE", movies.content.toString())
                        }
                    }
                }
                .launchIn(GlobalScope)
        })
        return view
    }
}