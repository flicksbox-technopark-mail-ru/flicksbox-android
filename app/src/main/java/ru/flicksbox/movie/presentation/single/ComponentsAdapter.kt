package ru.flicksbox.movie.presentation.single

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.flicksbox.R
import ru.flicksbox.movie.presentation.player.PlayClickListener

const val COMPONENTS_COUNT = 3
const val PREVIEW_VIEW_HOLDER = 0
const val OPTIONS_VIEW_HOLDER = 1
const val INFO_VIEW_HOLDER = 2

class ComponentsAdapter(
    private val playClickListener: PlayClickListener,
    private val favouritesClickListener: FavouritesClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataSource: FullMovieViewData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PREVIEW_VIEW_HOLDER -> createPreviewViewHolder(parent)
            INFO_VIEW_HOLDER -> createInfoViewHolder(parent)
            OPTIONS_VIEW_HOLDER -> createOptionsViewHolder(parent)
            else -> createInfoViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            PREVIEW_VIEW_HOLDER -> dataSource?.let { (holder as PreviewViewHolder).bind(it.toPreview()) }
            INFO_VIEW_HOLDER -> dataSource?.let { (holder as InfoViewHolder).bind(it.toInfo()) }
            OPTIONS_VIEW_HOLDER -> dataSource?.let { (holder as OptionsViewHolder).bind(it.id, it.isFavorite) }
        }
    }

    override fun getItemCount(): Int {
        return COMPONENTS_COUNT
    }

    override fun getItemViewType(position: Int): Int {
        return position % COMPONENTS_COUNT
    }

    private fun createPreviewViewHolder(parent: ViewGroup): PreviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_preview, parent, false)
        return PreviewViewHolder(view)
    }

    private fun createInfoViewHolder(parent: ViewGroup): InfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.component_info, parent, false)
        return InfoViewHolder(view)
    }

    private fun createOptionsViewHolder(parent: ViewGroup): OptionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_movie_options, parent, false)
        return OptionsViewHolder(view, playClickListener, favouritesClickListener)
    }

    fun updateData(movie: FullMovieViewData) {
        dataSource = movie
        notifyDataSetChanged()
    }
}

fun FullMovieViewData.toPreview(): PreviewViewData =
    PreviewViewData(
        this.images, this.name, this.directors
    )

fun FullMovieViewData.toInfo(): InfoViewData =
    InfoViewData(
        this.description,
        this.countries,
        this.genres,
        this.actors
    )