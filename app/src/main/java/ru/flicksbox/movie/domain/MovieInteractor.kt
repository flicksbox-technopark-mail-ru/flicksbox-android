package ru.flicksbox.movie.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.movie.presentation.MovieInteractor

class MovieInteractorImpl(private val movieRepository: MovieRepository) : MovieInteractor {
    override fun getUserMovies(): Flow<Data<FavouritesEntity>> {
        return movieRepository.getUserList()
    }

    override fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>> {
        return movieRepository.getTopMovies(15, 0)
    }

    override fun getLatestMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>> {
        return movieRepository.getLatestMovies(15, 0)
    }
}