package ru.flicksbox.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.flicksbox.movie.data.MoviesWrapperDTO
import ru.flicksbox.network.BaseDTO

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "flicksbox.db"
        const val DATABASE_VERSION = 1
        private const val SQL_CREATE_ENTRIES = ""
        private const val SQL_DELETE_ENTRIES = ""
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun getTopMovies(count: Int, from: Int) : BaseDTO<MoviesWrapperDTO> {
        readableDatabase.query()
    }
}