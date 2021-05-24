package ru.flicksbox.movie.presentation.single

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.flicksbox.R
import ru.flicksbox.utils.buildImageUrl

class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val posterView = itemView.findViewById<ImageView>(R.id.content_poster)
    private val largePreviewView = itemView.findViewById<TextView>(R.id.preview_title_l)
    private val smallPreviewView = itemView.findViewById<TextView>(R.id.preview_title_s)

    fun bind(movie: PreviewViewData) {
        val path = buildImageUrl(movie.poster)
        Picasso
            .with(itemView.context)
            .load(path)
            .into(posterView)
        largePreviewView.text = movie.movieTitle
        smallPreviewView.text = movie.directors.joinToString()
    }
}

data class PreviewViewData(
    val poster: String,
    val movieTitle: String,
    val directors: List<String>
)
