package ru.flicksbox.movie.presentation.single

data class FullMovieViewData(
    val actors: List<String>,
    val contentID: Int,
    val countries: List<String>,
    val description: String,
    val directors: List<String>,
    val genres: List<String>,
    val id: Int,
    val images: String,
    val isFavorite: Boolean,
    val isLike: Boolean,
    val isFree: Boolean,
    val name: String,
    val originalName: String,
    val rating: String,
    val shortDescription: String,
    val type: String,
    val video: String,
    val year: Int,
)
