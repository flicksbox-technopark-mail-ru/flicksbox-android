package ru.flicksbox.movie.data

import com.google.gson.annotations.SerializedName

data class QueryResultWrapperDTO(
    @SerializedName("result") val result : QueryResultDTO
)

data class QueryResultDTO(
    @SerializedName("movies") val movies : List<MovieDTO>,
    @SerializedName("tv_shows") val tvShows: List<TVShowDTO>,
    @SerializedName("actors") val actors : List<ActorDTO>
)
