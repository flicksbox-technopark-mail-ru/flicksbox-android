package ru.flicksbox.data

import java.io.IOException

data class AppError(val userMessage: String) : IOException()