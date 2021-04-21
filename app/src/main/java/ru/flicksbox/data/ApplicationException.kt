package ru.flicksbox.data

import java.io.IOException

data class ApplicationException(val errorCode: Int) : IOException()