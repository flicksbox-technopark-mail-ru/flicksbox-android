package ru.flicksbox.movie.presentation.search

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
import ru.flicksbox.movie.presentation.favourites.toViewData
import ru.flicksbox.movie.presentation.single.MovieClickListener
import ru.flicksbox.movie.presentation.single.SingleMovieFragment

class SearchFragment : Fragment(), MovieClickListener, SearchQueryInputListener {
    private var adapter: SearchAdapter? = null
    private var lastQuery : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val componentsRecycler = view.findViewById<RecyclerView>(R.id.search_components_recycler)
        adapter = SearchAdapter(emptyList(), this, this, lastQuery)
        componentsRecycler.adapter = adapter
        componentsRecycler.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onMovieClick(movieID: Int) {
        val fm = activity?.supportFragmentManager?.beginTransaction() ?: return
        val fragment = SingleMovieFragment.newInstance(movieID)
        fm.replace(R.id.search_fragment_layout, fragment)
        fm.addToBackStack(null).commit()
    }

    override fun onSearchQueryInput(query: String) {
        lastQuery = query
        App.movieInteractor.getMoviesByQuery(query, 8, 0)
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                when (result) {
                    is Data.Content -> adapter?.updateData(result.content.toViewData(), lastQuery)
                }
            }.launchIn(CoroutineScope(Dispatchers.Main))
    }
}