package ru.flicksbox.movie.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity


class SliderAdapter(list: List<MovieViewData> = listOf()): RecyclerView.Adapter<SliderHolder>() {
    var items: List<MovieViewData> = list.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_card, parent, false)
        return SliderHolder(view)
    }

    fun updateData(inputItems: List<MovieViewData>) {
        items = inputItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}