package ru.flicksbox.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MoviesWrapperDTO(@SerializedName("movies") val movies: List<MovieDTO>)

@Entity
data class MovieDTO(
    @SerializedName("actors") val actors: List<ActorDTO>?,
    @SerializedName("content_id") @ColumnInfo(name = "content_id") val contentID: Int,
    @SerializedName("countries") val countries: List<CountryDTO>?,
    @SerializedName("description") val description: String,
    @SerializedName("directors") val directors: List<DirectorDTO>?,
    @SerializedName("genres") val genres: List<GenreDTO>?,
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("images") val images: String,
    @SerializedName("is_favourite") @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @SerializedName("is_like") @ColumnInfo(name = "is_like") val isLike: Boolean,
    @SerializedName("is_free") @ColumnInfo(name = "is_free") val isFree: Boolean,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") @ColumnInfo(name = "original_name") val originalName: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("short_description") @ColumnInfo(name = "short_description") val shortDescription: String,
    @SerializedName("type") val type: String,
    @SerializedName("video") val video: String,
    @SerializedName("year") val year: Int,
)

data class ActorDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class CountryDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class DirectorDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class GenreDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)