package ru.flicksbox.movie.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.movie.presentation.MovieInteractor

class MovieInteractorImpl(private val movieRepository: MovieRepository) : MovieInteractor {
    override fun getUserMovies(): Flow<Data<FavouritesEntity>> {
        return movieRepository.getUserList()
    }
}