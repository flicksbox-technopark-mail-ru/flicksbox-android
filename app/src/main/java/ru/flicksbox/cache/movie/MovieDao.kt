package ru.flicksbox.cache.movie

import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieDB>

    @Query("SELECT * FROM movies ORDER BY year DESC")
    fun getNew(): List<MovieDB>

    @Query("SELECT * FROM movies ORDER BY rating DESC")
    fun getPopular(): List<MovieDB>

    @Query("SELECT * FROM movies WHERE movie_id = :id")
    fun getByID(id: Int): MovieDB

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavorite(): List<MovieDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDB)

    @Update
    fun update(movie: MovieDB)

    @Delete
    fun delete(movie: MovieDB)
}