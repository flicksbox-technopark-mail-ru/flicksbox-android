package ru.flicksbox.utils

import android.content.Context
import android.widget.Toast
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.ApiError
import ru.flicksbox.data.AppError

fun notifyError(t: Throwable, context: Context) {
    val message = when(t) {
        is ApiError -> t.userMessage
        is AppError -> t.userMessage
        else -> App.appContext().getString(R.string.unknown_Server_Error)
    }

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}