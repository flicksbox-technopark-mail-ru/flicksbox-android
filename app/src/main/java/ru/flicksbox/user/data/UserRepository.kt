package ru.flicksbox.user.data

import kotlinx.coroutines.flow.*
import ru.flicksbox.data.ApiNotRespondingError
import ru.flicksbox.data.Data
import ru.flicksbox.user.domain.UserEntity
import ru.flicksbox.user.domain.UserRepository

class UserRepositoryImpl(
    private val userService: UserService,
) : UserRepository {
    override fun getUser(): Flow<Data<UserEntity>> =
        flow { emit(userService.getUser()) }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingError()
                body.toDomain()
            }
            .map { Data.content(it) }
            .onStart { emit(Data.loading()) }
            .catch { emit(Data.error(it)) }

    override fun login(email: String, password: String): Flow<Data<UserEntity>> =
        flow { emit(userService.login(LoginBody(email, password))) }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingError()
                body.toDomain()
            }
            .map { Data.content(it) }
            .onStart { emit(Data.loading()) }
            .catch { emit(Data.error(it)) }
}

private fun UserWrapperDTO.toDomain(): UserEntity =
    UserEntity(this.user.id, this.user.avatar, this.user.email, this.user.nickname)