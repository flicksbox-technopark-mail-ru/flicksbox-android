package ru.flicksbox.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Data<T> {
    data class Content<T>
    @Deprecated("Please, don't call this constructor directly, use Data.content() method") constructor(
        val content: T
    ) : Data<T>()

    data class Error<T>
    @Deprecated("Please, don't call this constructor directly, use Data.error() method") constructor(
        val throwable: Throwable,
        val content: T? = null
    ) : Data<T>()

    data class Loading<T>
    @Deprecated("Please, don't call this constructor directly, use Data.loading() method") constructor(
        val content: T? = null
    ) : Data<T>()

    companion object {
        inline fun <reified T> content(content: T): Data<T> = Content(content)
        inline fun <reified T> error(throwable: Throwable, content: T? = null): Data<T> = Error(throwable, content)
        inline fun <reified T> loading(content: T? = null): Data<T> = Loading(content)
    }
}

fun <T, R> Flow<Data<T>>.mapData(transform: suspend (value: T) -> R): Flow<Data<R>> =
    map { data ->
        when (data) {
            is Data.Content -> Data.Content(transform(data.content))
            is Data.Error -> Data.Error(data.throwable, data.content?.let { transform(it) })
            is Data.Loading -> Data.Loading(data.content?.let { transform(it) })
        }
    }