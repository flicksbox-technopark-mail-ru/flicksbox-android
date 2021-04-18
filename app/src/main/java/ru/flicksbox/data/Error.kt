package ru.flicksbox.data

import java.io.IOException

data class ApiError(
    val code: Int,
    val apiMessage: String,
    val userMessage: String,
) : IOException()

fun ApiNotRespondingError(): ApiError = ApiError(
    500,
    "service not responding",
    "Service not available",
)