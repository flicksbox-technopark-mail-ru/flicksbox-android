package ru.flicksbox.user.domain

import android.util.Patterns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.flicksbox.data.ApplicationException
import ru.flicksbox.data.Data
import ru.flicksbox.user.presentation.UserInteractor
import ru.flicksbox.utils.EMAIL_INCORRECT_ERROR
import ru.flicksbox.utils.FIELDS_ARE_EMPTY_ERROR
import ru.flicksbox.utils.PASSWORD_INCORRECT_ERROR
import ru.flicksbox.utils.REPEATED_PASSWORD_INCORRECT_ERROR

const val MIN_PASSWORD_LENGTH = 0

class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {
    override fun login(email: String, password: String): Flow<Data<UserEntity>> {
        if (email.isEmpty() || password.isEmpty()) {
            return flow { emit(Data.error(ApplicationException(FIELDS_ARE_EMPTY_ERROR))) }
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(Data.error(ApplicationException(EMAIL_INCORRECT_ERROR))) }
        }

        return userRepository.login(email, password)
    }

    override fun getUser(): Flow<Data<UserEntity>> {
        return userRepository.getUser()
    }

    override fun updatePassword(oldPassword: String,
                                newPassword: String,
                                repeatedPassword: String): Flow<Data<UserEntity>> {
        if (newPassword.length < MIN_PASSWORD_LENGTH) {
            return flow { emit(Data.error(ApplicationException(PASSWORD_INCORRECT_ERROR))) }
        }

        if (newPassword != repeatedPassword) {
            return flow { emit(Data.error(ApplicationException(REPEATED_PASSWORD_INCORRECT_ERROR))) }
        }
        return userRepository.updatePassword(oldPassword, newPassword, repeatedPassword)
    }

    override fun updateUserInfo(username: String, email: String): Flow<Data<UserEntity>> {
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(Data.error(ApplicationException(EMAIL_INCORRECT_ERROR))) }
        }

        return userRepository.updateUserInfo(username, email)
    }

    override fun signup(
        username: String,
        email: String,
        password: String,
        repeatedPassword: String
    ): Flow<Data<UserEntity>> {
        if (email.isEmpty() || password.isEmpty()) {
            return flow { emit(Data.error(ApplicationException(FIELDS_ARE_EMPTY_ERROR))) }
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return flow { emit(Data.error(ApplicationException(EMAIL_INCORRECT_ERROR))) }
        }

        if (password.length < MIN_PASSWORD_LENGTH) {
            return flow { emit(Data.error(ApplicationException(PASSWORD_INCORRECT_ERROR))) }
        }

        if (password != repeatedPassword) {
            return flow { emit(Data.error(ApplicationException(REPEATED_PASSWORD_INCORRECT_ERROR))) }
        }

        return userRepository.signup(username, email, password, repeatedPassword)
    }
}