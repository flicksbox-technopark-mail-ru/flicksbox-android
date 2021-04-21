package ru.flicksbox.user.presentation

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.user.domain.UserEntity

interface UserInteractor {
    fun login(email: String, password: String): Flow<Data<UserEntity>>
    fun signup(username: String, email: String, password: String, repeatedPassword: String): Flow<Data<UserEntity>>
    fun getUser(): Flow<Data<UserEntity>>
}