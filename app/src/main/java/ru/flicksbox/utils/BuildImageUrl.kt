package ru.flicksbox.utils

const val IMAGE_API = "https://www.flicksbox.ru"
const val DEFAULT_SIZE = "/640"
const val AVATARS_PATH = "avatars"

fun buildImageUrl(baseUrl: String): String {
    if (baseUrl.contains(AVATARS_PATH)) {
        return IMAGE_API + baseUrl
    }
    return IMAGE_API + baseUrl + DEFAULT_SIZE
}

fun buildVideoUrl(moviePath: String): String {
    return IMAGE_API + moviePath
}