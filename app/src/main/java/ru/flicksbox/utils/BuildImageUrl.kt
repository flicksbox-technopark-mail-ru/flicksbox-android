package ru.flicksbox.utils

import ru.flicksbox.network.NetworkServiceFactory

const val IMAGE_API = "https://www.flicksbox.ru"
const val DEFAULT_SIZE = "/640"

fun buildImageUrl(baseUrl: String): String {
    return IMAGE_API + baseUrl + DEFAULT_SIZE
}