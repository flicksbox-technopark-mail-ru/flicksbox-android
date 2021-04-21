package ru.flicksbox.data

import java.io.IOException

data class ApiException(
    val code: Int,
    val apiMessage: String,
    val userMessage: String,
) : IOException()

fun ApiNotRespondingException(): ApiException = ApiException(
    500,
    "service not responding",
    "Service not available",
)