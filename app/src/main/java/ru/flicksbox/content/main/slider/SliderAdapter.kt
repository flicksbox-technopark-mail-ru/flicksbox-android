package ru.flicksbox.content.main.slider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.domain.MovieEntity


class SliderAdapter(list: List<MovieEntity> = listOf()): RecyclerView.Adapter<SliderHolder>() {
    var items: List<MovieEntity> = list.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return SliderHolder(view)
    }

    fun updateData(inputItems: List<MovieEntity>) {
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