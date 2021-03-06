package ru.flicksbox.movie.domain

data class MovieEntity(
    val actors: List<ActorEntity>?,
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
    val video: String,
    val year: Int,
)

data class ActorEntity(val id: Int, val name: String)

data class CountryEntity(val id: Int, val name: String)

data class DirectorEntity(val id: Int, val name: String)

data class GenreEntity(val id: Int, val name: String)
