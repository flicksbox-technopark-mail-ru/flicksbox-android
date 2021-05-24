package ru.flicksbox.movie.data

import com.google.gson.annotations.SerializedName

data class FavouritesRequestDTO(
    @SerializedName("content_id") val contentID : Int
)

data class FavouritesResponseDTO(
    @SerializedName("favourite") val favorite: ContentIDDTO
)

data class ContentIDDTO(
    @SerializedName("content_id") val contentID: Int
)
