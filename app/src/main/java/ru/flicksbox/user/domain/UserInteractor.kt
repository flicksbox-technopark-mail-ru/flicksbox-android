package ru.flicksbox.user.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.flicksbox.App
import ru.flicksbox.R
import ru.flicksbox.data.AppError
import ru.flicksbox.data.Data
import ru.flicksbox.user.presentation.UserInteractor

const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {
    private val emailRegex: Regex by lazy {
        Regex(emailPattern)
    }

    override fun login(email: String, password: String): Flow<Data<UserEntity>> {
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

        return userRepository.login(email, password)
    }

    override fun getUser(): Flow<Data<UserEntity>> {
        return userRepository.getUser()
    }
}