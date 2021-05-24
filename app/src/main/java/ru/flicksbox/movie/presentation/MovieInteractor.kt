package ru.flicksbox.movie.presentation

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.FavouritesEntity
import ru.flicksbox.movie.domain.MovieEntity
import ru.flicksbox.user.domain.ResultEntity

interface MovieInteractor {
    fun getUserMovies(): Flow<Data<FavouritesEntity>>
    fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
    fun getLatestMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
    fun getMovie(id :Int): Flow<Data<MovieEntity>>
    fun addToFavourites(id: Int): Flow<Data<ResultEntity>>
    fun deleteFromFavourites(id: Int): Flow<Data<ResultEntity>>
}