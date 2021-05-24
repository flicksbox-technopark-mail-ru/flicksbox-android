package ru.flicksbox.movie.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.user.domain.ResultEntity

interface MovieRepository {
    fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
    fun getUserList(): Flow<Data<FavouritesEntity>>
    fun getLatestMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
    fun getMovie(id: Int): Flow<Data<MovieEntity>>
    fun addToFavourites(id: Int): Flow<Data<ResultEntity>>
    fun deleteFromFavourites(id: Int): Flow<Data<ResultEntity>>
}