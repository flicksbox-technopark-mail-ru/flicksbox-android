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

    override fun getUserList(): Flow<Data<FavouritesEntity>> =
        flow { emit(movieService.getUserMovies()) }
            .map { userList ->
                val body = userList.body ?: throw ApiNotRespondingException()
                body.favourites.toDomain()
            }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }
}

fun FavouritesWrapperDTO.toDomain(): FavouritesEntity =
    FavouritesEntity(
        this.movies.map { it.toDomain() },
        this.tvShows.map { it.toDomain() })

fun TVShowDTO.toDomain(): TVShowEntity =
    TVShowEntity(
        this.actors?.map { it.toDomain() },
        this.seasons,
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
        this.year
    )

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

fun ActorDTO.toDomain(): ActorEntity =
    ActorEntity(this.id, this.name)

fun CountryDTO.toDomain(): CountryEntity =
    CountryEntity(this.id, this.name)

fun DirectorDTO.toDomain(): DirectorEntity =
    DirectorEntity(this.id, this.name)

fun GenreDTO.toDomain(): GenreEntity =
    GenreEntity(this.id, this.name)

