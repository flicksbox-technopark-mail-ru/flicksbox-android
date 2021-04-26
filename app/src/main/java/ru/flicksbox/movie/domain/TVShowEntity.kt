package ru.flicksbox.movie.domain

data class TVShowEntity(
    val actors: List<ActorEntity>?,
    val seasons: Int,
    val contentID: Int,
    val countries: List<CountryEntity>?,
    val description: String,
    val directors: List<DirectorEntity>?,
    val genres: List<GenreEntity>?,
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
    val year: Int,
)
