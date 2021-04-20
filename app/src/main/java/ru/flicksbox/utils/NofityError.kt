package ru.flicksbox.utils

import android.content.Context
import android.widget.Toast
import ru.flicksbox.data.ApiException
import ru.flicksbox.data.ApplicationException

fun notifyError(t: Throwable, context: Context) {
    val message = when (t) {
        is ApiException -> t.userMessage
        is ApplicationException -> mapErrorToString(t.errorCode)
        else -> getUnknownError()
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}