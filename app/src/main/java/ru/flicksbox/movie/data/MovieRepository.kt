package ru.flicksbox.movie.data

import kotlinx.coroutines.flow.*
import ru.flicksbox.cache.movie.*
import ru.flicksbox.data.ApiNotRespondingException
import ru.flicksbox.data.Data
import ru.flicksbox.movie.domain.*
import ru.flicksbox.user.domain.ResultEntity

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) : MovieRepository {
    override fun getTopMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>> =
        flow { emit(movieService.getTopMovies(count, from)) }
            .map { movies ->
                val body = movies.body ?: throw ApiNotRespondingException()
                body.movies.map { it.toDomain() }
            }
            .onStart { Data.content(emit(movieDao.getPopular().map { it.toDomain() })) }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }

    override fun getUserList(): Flow<Data<FavouritesEntity>> =
        flow { emit(movieService.getUserMovies()) }
            .map { userList ->
                val body = userList.body ?: throw ApiNotRespondingException()
                body.favourites.toDomain()
            }
            .onStart { Data.content(
                emit(FavouritesEntity(movieDao.getFavorite().map { it.toDomain() }, emptyList()))
            ) }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }

    override fun getLatestMovies(count: Int, from: Int): Flow<Data<List<MovieEntity>>> =
        flow { emit(movieService.getLatestMovies(count, from)) }
            .map { movies ->
                val body = movies.body ?: throw ApiNotRespondingException()
                body.movies.map { it.toDomain() }
            }
            .onStart { Data.content(emit(movieDao.getNew().map { it.toDomain() })) }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }

    override fun getMovie(id: Int): Flow<Data<MovieEntity>> =
        flow { emit(movieService.getMovie(id)) }
            .map { movie ->
                val body = movie.body ?: throw ApiNotRespondingException()
                val movieEntity = body.movie.toDomain()
                movieDao.insert(movieEntity.toDB())
                movieEntity
            }
            .onStart { Data.content(emit(movieDao.getByID(id).toDomain())) }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }

    override fun addToFavourites(id: Int): Flow<Data<ResultEntity>> =
        flow { emit(movieService.addToFavorites(FavouritesRequestDTO(id))) }
            .map { response ->
                val body = response.body ?: throw ApiNotRespondingException()
                ResultEntity(true)
            }
            .map { Data.content(it) }
            .catch { emit(Data.error(it)) }

    override fun deleteFromFavourites(id: Int): Flow<Data<ResultEntity>> =
        flow { emit(movieService.deleteFromFavourites(FavouritesRequestDTO(id))) }
            .map { response ->
                val body = response.body ?: throw ApiNotRespondingException()
                ResultEntity(true)
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

fun MovieEntity.toDB(isNew: Boolean = false, isPopular: Boolean = false): MovieDB =
    MovieDB(
        this.id,
        this.actors?.map { ActorDB(it.id, it.name) } ?: emptyList(),
        this.contentID,
        this.countries?.map { CountryDB(it.id, it.name) } ?: emptyList(),
        this.description,
        this.directors?.map { DirectorDB(it.id, it.name) } ?: emptyList(),
        this.genres?.map { GenreDB(it.id, it.name) } ?: emptyList(),
        this.images,
        this.isFavorite,
        this.name,
        this.video,
        isNew,
        isPopular,
        this.year,
        this.rating,
        this.type
    )

fun MovieDB.toDomain(): MovieEntity =
    MovieEntity(
        this.actors.map { ActorEntity(it.id, it.name) },
        this.contentID,
        this.countries.map { CountryEntity(it.id, it.name) },
        this.description,
        this.directors.map { DirectorEntity(it.id, it.name) },
        this.genres.map { GenreEntity(it.id, it.name) },
        this.contentID,
        this.images,
        this.isFavorite,
        false,
        false,
        this.name,
        this.name,
        this.rating,
        this.description,
        this.type,
        this.video,
        this.year
    )