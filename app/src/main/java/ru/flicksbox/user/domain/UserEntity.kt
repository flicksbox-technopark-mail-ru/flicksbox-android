package ru.flicksbox.user.domain

data class UserEntity(
    val id: Int,
    val avatar: String,
    val email: String,
    val nickname: String,
)