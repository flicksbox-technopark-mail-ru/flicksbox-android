package ru.flicksbox.movie.domain

data class MovieEntity(
    val actors: List<MovieActorEntity>?,
    val contentID: Int,
    val countries: List<MovieCountryEntity>?,
    val description: String,
    val directors: List<MovieDirectorEntity>?,
    val genres: List<MovieGenreEntity>?,
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

data class MovieActorEntity(val id: Int, val name: String)

data class MovieCountryEntity(val id: Int, val name: String)

data class MovieDirectorEntity(val id: Int, val name: String)

data class MovieGenreEntity(val id: Int, val name: String)
