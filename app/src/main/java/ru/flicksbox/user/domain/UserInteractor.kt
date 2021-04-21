package ru.flicksbox.user.domain

import android.util.Patterns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.flicksbox.data.ApplicationException
import ru.flicksbox.data.Data
import ru.flicksbox.user.presentation.UserInteractor
import ru.flicksbox.utils.EMAIL_INCORRECT_ERROR
import ru.flicksbox.utils.FIELDS_ARE_EMPTY_ERROR

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

    override fun signup(
        username: String,
        email: String,
        password: String,
        repeated_password: String
    ): Flow<Data<UserEntity>> {
        if (email.isEmpty() || password.isEmpty()) {
            val errorMessage =
                App.appContext().resources.getString(R.string.auth_Required_Fields_Empty_Error)
            return flow { emit(Data.error(AppError(errorMessage))) }
        }

        if (!email.matches(emailRegex)) {
            val errorMessage =
                App.appContext().resources.getString(R.string.auth_Email_Incorrect_Error)
            return flow { emit(Data.error(AppError(errorMessage))) }
        }

        if (password.length < 6) {
            val errorMessage =
                App.appContext().resources.getString(R.string.registration_Password_Incorrect_Password)
            return flow { emit(Data.error(AppError(errorMessage))) }
        }

        if (password != repeated_password) {
            val errorMessage =
                App.appContext().resources.getString(R.string.registration_Password_Incorrect_RepeatedPassword)
            return flow { emit(Data.error(AppError(errorMessage))) }
        }

        return userRepository.signup(username, email, password, repeated_password)
    }
}