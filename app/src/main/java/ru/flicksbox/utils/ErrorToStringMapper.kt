package ru.flicksbox.utils

import ru.flicksbox.App
import ru.flicksbox.R

const val FIELDS_ARE_EMPTY_ERROR = 0
const val EMAIL_INCORRECT_ERROR = 1

fun mapErrorToString(errorCode: Int): String {
    val resourceID = when (errorCode) {
        FIELDS_ARE_EMPTY_ERROR -> R.string.auth_required_fields_empty_error
        EMAIL_INCORRECT_ERROR -> R.string.auth_email_incorrect_error
        else -> R.string.unknown_server_error
    }
    return App.appContext().getString(resourceID)
}

fun getUnknownError(): String {
    return App.appContext().getString(R.string.unknown_server_error)
}