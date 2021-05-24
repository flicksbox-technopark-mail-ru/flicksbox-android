package ru.flicksbox.movie.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.flicksbox.network.BaseDTO

interface MovieService {

    @GET("movies/top")
    suspend fun getTopMovies(@Query("count") count: Int, @Query("from") from: Int): BaseDTO<MoviesWrapperDTO>

    @GET("favourites")
    suspend fun getUserMovies(): BaseDTO<UserListDTO>

    @GET("movies/latest")
    suspend fun getLatestMovies(@Query("count") count: Int, @Query("from") from: Int): BaseDTO<MoviesWrapperDTO>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: Int): BaseDTO<MovieWrapperDTO>
}