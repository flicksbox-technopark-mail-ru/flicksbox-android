package ru.flicksbox.movie.presentation

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.MovieEntity

interface MovieInteractor {
    fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>>
}