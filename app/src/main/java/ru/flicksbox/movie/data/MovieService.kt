package ru.flicksbox.movie.data

import retrofit2.http.*
import ru.flicksbox.network.BaseDTO
import ru.flicksbox.user.data.InfoDTO

interface MovieService {

    @GET("movies/top")
    suspend fun getTopMovies(
        @Query("count") count: Int,
        @Query("from") from: Int
    ): BaseDTO<MoviesWrapperDTO>

    @GET("favourites")
    suspend fun getUserMovies(): BaseDTO<UserListDTO>

    @GET("movies/latest")
    suspend fun getLatestMovies(
        @Query("count") count: Int,
        @Query("from") from: Int
    ): BaseDTO<MoviesWrapperDTO>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: Int): BaseDTO<MovieWrapperDTO>

    @POST("favourites")
    suspend fun addToFavorites(@Body contentID: FavouritesRequestDTO): BaseDTO<FavouritesResponseDTO>

    @HTTP(method = "DELETE", path = "favourites", hasBody = true)
    suspend fun deleteFromFavourites(@Body contentID: FavouritesRequestDTO): InfoDTO

    @GET("search")
    suspend fun getMoviesByQuery(
        @Query("q") query: String,
        @Query("count") count: Int,
        @Query("from") from: Int
    ): BaseDTO<QueryResultWrapperDTO>
}