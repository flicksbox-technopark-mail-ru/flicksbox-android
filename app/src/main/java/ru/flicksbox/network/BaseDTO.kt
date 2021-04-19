package ru.flicksbox.network

import com.google.gson.annotations.SerializedName

data class BaseDTO<T>(
    @SerializedName("body") val body: T?,
    @SerializedName("error") val error: ErrorDTO?,
)

data class ErrorDTO(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("user_message") val userMessage: String
)