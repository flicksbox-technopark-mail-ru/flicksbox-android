package ru.flicksbox.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.flicksbox.App
import ru.flicksbox.cache.movie.MovieDB
import ru.flicksbox.cache.movie.MovieDao

const val DATABASE_NAME = "movie_database"

@Database(entities = [MovieDB::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {

        private var INSTANCE: MovieDatabase? = null

        fun getInstance(): MovieDatabase {
            return this.INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    App.appContext(),
                    MovieDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}