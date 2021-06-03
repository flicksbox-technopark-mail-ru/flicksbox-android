package ru.flicksbox.movie.domain

data class FavouritesEntity(val movies: List<MovieEntity>, var tvShows: List<TVShowEntity>)