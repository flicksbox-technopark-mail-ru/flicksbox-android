package ru.flicksbox.user.domain

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data

interface UserRepository {
    fun getUser(): Flow<Data<UserEntity>>
    fun login(email: String, password: String): Flow<Data<UserEntity>>
}