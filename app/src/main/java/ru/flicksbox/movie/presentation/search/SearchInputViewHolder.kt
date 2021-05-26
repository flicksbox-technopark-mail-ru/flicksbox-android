package ru.flicksbox.movie.presentation.search

import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R

interface SearchQueryInputListener {
    fun onSearchQueryInput(query: String)
}

class SearchInputViewHolder(itemView: View, private val query: String) : RecyclerView.ViewHolder(itemView) {
    private val inputQuery = itemView.findViewById<EditText>(R.id.search_query_input)

    fun bind(inputListener: SearchQueryInputListener) {
        inputQuery.addTextChangedListener { query ->
            inputListener.onSearchQueryInput(query.toString())
        }
    }
}