package ru.flicksbox.movie.data

import kotlinx.coroutines.flow.*
import ru.flicksbox.data.ApiNotRespondingException
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.*

class MovieRepositoryImpl(
    private val movieService: MovieService,
) : MovieRepository {
    override fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>> =
        flow { emit(movieService.getTopMovies(count, from)) }
            .map { movies ->
                val body = movies.body ?: throw ApiNotRespondingException()
                body.movies.map { it.toDomain() }
            }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }
}

fun MovieDTO.toDomain(): MovieEntity =
    MovieEntity(
        this.actors?.map { it.toDomain() },
        this.contentID,
        this.countries?.map { it.toDomain() },
        this.description,
        this.directors?.map { it.toDomain() },
        this.genres?.map { it.toDomain() },
        this.contentID,
        this.images,
        this.isFavorite,
        this.isLike,
        this.isFree,
        this.name,
        this.originalName,
        this.rating,
        this.shortDescription,
        this.type,
        this.video,
        this.year
    )

fun MovieActorDTO.toDomain(): MovieActorEntity =
    MovieActorEntity(this.id, this.name)

fun MovieCountryDTO.toDomain(): MovieCountryEntity =
    MovieCountryEntity(this.id, this.name)

fun MovieDirectorDTO.toDomain(): MovieDirectorEntity =
    MovieDirectorEntity(this.id, this.name)

fun MovieGenreDTO.toDomain(): MovieGenreEntity =
    MovieGenreEntity(this.id, this.name)

