package ru.flicksbox.user.presentation

import kotlinx.coroutines.flow.Flow
import ru.flicksbox.data.Data
import ru.flicksbox.user.domain.ResultEntity
import ru.flicksbox.user.domain.UserEntity

interface UserInteractor {
    fun login(email: String, password: String): Flow<Data<UserEntity>>
    fun signup(username: String, email: String, password: String, repeatedPassword: String): Flow<Data<UserEntity>>
    fun getUser(): Flow<Data<UserEntity>>
    fun updatePassword(oldPassword: String, newPassword: String, repeatedPassword: String): Flow<Data<UserEntity>>
    fun updateUserInfo(username: String, email: String): Flow<Data<UserEntity>>
    fun logout(): Flow<Data<ResultEntity>>
}