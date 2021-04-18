package ru.flicksbox.user.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data

interface UserRepository {
    fun getUser(cookie: String): Flow<Data<UserEntity>>
}