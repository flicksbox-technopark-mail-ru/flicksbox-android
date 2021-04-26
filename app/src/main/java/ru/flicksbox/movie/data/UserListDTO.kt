package ru.flicksbox.movie.data

import com.google.gson.annotations.SerializedName

data class UserListDTO(
    @SerializedName("favourites") val favourites: FavouritesWrapperDTO
)

data class FavouritesWrapperDTO(
    @SerializedName("movies") val movies: List<MovieDTO>,
    @SerializedName("tv_shows") val tvShows: List<TVShowDTO>,
)