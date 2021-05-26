package ru.flicksbox.movie.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.movie.presentation.MovieInteractor
import ru.flicksbox.user.domain.ResultEntity

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

    override fun getMovie(id: Int): Flow<Data<MovieEntity>> {
        return movieRepository.getMovie(id)
    }

    override fun addToFavourites(id: Int): Flow<Data<ResultEntity>> {
        return movieRepository.addToFavourites(id)
    }

    override fun deleteFromFavourites(id: Int): Flow<Data<ResultEntity>> {
        return movieRepository.deleteFromFavourites(id)
    }

    override fun getMoviesByQuery(query: String, count: Int, from: Int): Flow<Data<FavouritesEntity>> {
        return movieRepository.getMoviesByQuery(query, count, from)
    }
}