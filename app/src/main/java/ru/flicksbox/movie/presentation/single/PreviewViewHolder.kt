package ru.flicksbox.movie.presentation.single

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.flicksbox.R
import ru.flicksbox.utils.buildImageUrl

class PreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val posterView = itemView.findViewById<ImageView>(R.id.content_poster)
    private val largePreviewView = itemView.findViewById<TextView>(R.id.preview_title_l)
    private val smallPreviewView = itemView.findViewById<TextView>(R.id.preview_title_s)

    fun bind(movie: PreviewViewData) {
        largePreviewView.text = movie.movieTitle
        smallPreviewView.text = movie.directors.joinToString()
        val path = buildImageUrl(movie.poster)
        Glide.with(itemView)
            .load(path)
            .placeholder(R.drawable.pulpfiction_large)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(posterView)
    }
}

data class PreviewViewData(
    val poster: String,
    val movieTitle: String,
    val directors: List<String>
)
