package ru.flicksbox.cache.movie

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "movies")
@TypeConverters(MovieConverters::class)
data class MovieDB(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieID: Int,
    @ColumnInfo(name = "actors")
    val actors: List<ActorDB>,
    @ColumnInfo(name = "content_id")
    val contentID: Int,
    @ColumnInfo(name = "countries")
    val countries: List<CountryDB>,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "directors")
    val directors: List<DirectorDB>,
    @ColumnInfo(name = "genres")
    val genres: List<GenreDB>,
    @ColumnInfo(name = "images")
    val images: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "video")
    val video: String,
    @ColumnInfo(name = "is_new")
    val isNew: Boolean = false,
    @ColumnInfo(name = "is_popular")
    val isPopular: Boolean = false,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "rating")
    val rating: String,
    @ColumnInfo(name = "type")
    val type: String,
)

data class ActorDB(val id: Int, val name: String)

data class CountryDB(val id: Int, val name: String)

data class DirectorDB(val id: Int, val name: String)

data class GenreDB(val id: Int, val name: String)

class MovieConverters {
    @TypeConverter
    fun fromActorDB(value: List<ActorDB>): String? {
        val result = Gson().toJson(value)
        return result
    }
    @TypeConverter
    fun toActorDB(value: String): List<ActorDB> {
        val result = fromJson<List<ActorDB>>(value)
        return result
    }
    @TypeConverter
    fun fromCountryDB(value: List<CountryDB>) = Gson().toJson(value)
    @TypeConverter
    fun toCountryDB(value: String) = fromJson<List<CountryDB>>(value)
    @TypeConverter
    fun fromDirectorDB(value: List<DirectorDB>) = Gson().toJson(value)
    @TypeConverter
    fun toDirectorDB(value: String) = fromJson<List<DirectorDB>>(value)
    @TypeConverter
    fun fromGenreDB(value: List<GenreDB>) = Gson().toJson(value)
    @TypeConverter
    fun toGenreDB(value: String) = fromJson<List<GenreDB>>(value)
}

inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson(json, object: TypeToken<T>(){}.type)
}