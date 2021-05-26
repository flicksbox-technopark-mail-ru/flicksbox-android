package ru.flicksbox.movie.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.favourites.MovieViewData
import ru.flicksbox.movie.presentation.single.MovieClickListener

const val COMPONENT_COUNT = 3
const val INPUT_COMPONENT = 0
const val FILTER_COMPONENT = 1
const val RESULT_COMPONENT = 2

class SearchAdapter(
    private var dataSource: List<MovieViewData>,
    private val searchQueryInputListener: SearchQueryInputListener,
    private val movieClickListener: MovieClickListener,
    private var query : String,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), FilterButtonClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INPUT_COMPONENT -> createSearchInputViewHolder(parent)
            FILTER_COMPONENT -> createSearchFiltersViewHolder(parent)
            RESULT_COMPONENT -> createSearchResultViewHolder(parent)
            else -> createSearchInputViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchInputViewHolder -> holder.bind(searchQueryInputListener)
            is SearchFiltersViewHolder -> holder.bind(this)
            is SearchResultViewHolder -> holder.bind(dataSource, movieClickListener)
        }
    }

    override fun getItemCount(): Int {
        return COMPONENT_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return position % COMPONENT_COUNT
    }

    private fun createSearchInputViewHolder(parent: ViewGroup): SearchInputViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_search_input, parent, false)
        return SearchInputViewHolder(view, query)
    }

    private fun createSearchFiltersViewHolder(parent: ViewGroup): SearchFiltersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_search_filters, parent, false)
        return SearchFiltersViewHolder(view)
    }

    private fun createSearchResultViewHolder(parent: ViewGroup): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_search_result, parent, false)
        return SearchResultViewHolder(view)
    }

    fun updateData(data: List<MovieViewData>, lastQuery : String) {
        dataSource = data
        query = lastQuery
        notifyDataSetChanged()
    }

    override fun onRatingButtonClick() {
        dataSource = dataSource.sortedBy { it.rating }
        notifyDataSetChanged()
    }

    override fun onYearButtonClick() {
        dataSource = dataSource.sortedBy { it.year }
        notifyDataSetChanged()
    }
}