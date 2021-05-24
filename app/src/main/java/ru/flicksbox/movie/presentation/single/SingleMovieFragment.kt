package ru.flicksbox.movie.presentation.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.movie.presentation.player.PlayClickListener
import ru.flicksbox.movie.presentation.player.PlayerFragment

const val MOVIE_ID = "movie_id"
const val DEFAULT_MOVIE_ID = 0

class SingleMovieFragment : Fragment(), PlayClickListener {
    private var movieID: Int? = null
    private var movieUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieID = it.getInt(MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_movie, container, false)
        val componentsRecycler = view.findViewById<RecyclerView>(R.id.single_movie_recycler)
        val adapter = ComponentsAdapter(this)
        componentsRecycler.adapter = adapter
        componentsRecycler.layoutManager = LinearLayoutManager(requireContext())

        App.movieInteractor.getMovie(movieID ?: DEFAULT_MOVIE_ID)
            .flowOn(Dispatchers.IO)
            .onEach { movie ->
                when (movie) {
                    is Data.Content -> {
                        adapter.updateData(movie.content.toViewData())
                        movieUrl = movie.content.video
                    }

                    is Data.Error -> {

                    }

                    is Data.Loading -> {

                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(movieID: Int) =
            SingleMovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(MOVIE_ID, movieID)
                }
            }
    }

    override fun onPlayClick() {
        movieUrl?.let {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.single_movie_layout, PlayerFragment.newInstance(it))
                ?.addToBackStack(null)?.commit()
        }
    }
}

fun MovieEntity.toViewData(): FullMovieViewData =
    FullMovieViewData(
        if (this.actors == null) listOf("") else this.actors.map { it.name },
        this.contentID,
        if (this.countries == null) listOf("") else this.countries.map { it.name },
        this.description,
        if (this.directors == null) listOf("") else this.directors.map { it.name },
        if (this.genres == null) listOf("") else this.genres.map { it.name },
        this.contentID,
        this.images,
        this.isFavorite,
        this.isLike,
        this.isFree,
        this.name,
        this.originalName,
        this.rating,
        this.shortDescription,
        this.type,
        this.video,
        this.year
    )

