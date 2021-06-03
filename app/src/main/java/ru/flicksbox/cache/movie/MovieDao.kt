package ru.flicksbox.cache.movie

import androidx.room.*

const val MOVIE_DOES_NOT_EXIST = 0

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieDB>

    @Query("SELECT * FROM movies ORDER BY year DESC")
    fun getNew(): List<MovieDB>

    @Query("SELECT * FROM movies ORDER BY rating DESC")
    fun getPopular(): List<MovieDB>

    @Query("SELECT * FROM movies WHERE movie_id = :id")
    fun getByID(id: Int): MovieDB?

    @Query("UPDATE movies SET images = :image WHERE movie_id = :id")
    fun updateImage(id: Int, image: String): Int

    @Query("UPDATE movies SET is_favorite = :isFavourite WHERE movie_id = :id")
    fun updateFavourite(id : Int, isFavourite : Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDB)

    @Update
    fun update(movie: MovieDB)

    @Delete
    fun delete(movie: MovieDB)
}