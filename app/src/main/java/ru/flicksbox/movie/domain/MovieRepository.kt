package ru.flicksbox.movie.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data

interface MovieRepository {
    fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
    fun getUserList(): Flow<Data<FavouritesEntity>>
    fun getLatestMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
}