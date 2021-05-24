package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class InfoDTO(
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("body") val body: String?,
)