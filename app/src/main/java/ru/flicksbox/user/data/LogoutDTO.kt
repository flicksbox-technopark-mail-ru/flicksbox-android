package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class LogoutDTO(
    @SerializedName("error") val error: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("body") val body: String?,
)