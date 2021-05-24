package ru.flicksbox.user.data

import kotlinx.coroutines.flow.*
import ru.flicksbox.data.ApiNotRespondingException
import ru.flicksbox.data.Data
import ru.flicksbox.user.domain.ResultEntity
import ru.flicksbox.user.domain.UserEntity
import ru.flicksbox.user.domain.UserRepository

class UserRepositoryImpl(
    private val userService: UserService,
) : UserRepository {
    override fun getUser(): Flow<Data<UserEntity>> =
        flow { emit(userService.getUser()) }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingException()
                body.toDomain()
            }
            .map { Data.content(it) }
            .onStart { emit(Data.loading()) }
            .catch { emit(Data.error(it)) }

    override fun login(email: String, password: String): Flow<Data<UserEntity>> =
        flow { emit(userService.login(LoginRequestDTO(email, password))) }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingException()
                body.toDomain()
            }
            .map { Data.content(it) }
            .onStart { emit(Data.loading()) }
            .catch { emit(Data.error(it)) }

    override fun signup(
        username: String,
        email: String,
        password: String,
        repeatedPassword: String
    ): Flow<Data<UserEntity>> =
        flow {
            emit(
                userService.signup(
                    SignupRequestDTO(
                        username,
                        email,
                        password,
                        repeatedPassword
                    )
                )
            )
        }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingException()
                body.toDomain()
            }
            .map {
                Data.content(it)
            }
            .onStart {
                emit(Data.loading())
            }
            .catch { emit(Data.error(it)) }

    override fun updatePassword(
        oldPassword: String,
        newPassword: String,
        repeatedPassword: String
    ): Flow<Data<UserEntity>> =
        flow {
            emit(
                userService.updatePassword(
                    ProfilePasswordRequestDTO(
                        oldPassword,
                        newPassword,
                        repeatedPassword
                    )
                )
            )
        }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingException()
                body.toDomain()
            }
            .map {
                Data.content(it)
            }
            .onStart {
                emit(Data.loading())
            }
            .catch { emit(Data.error(it)) }

    override fun updateUserInfo(username: String, email: String): Flow<Data<UserEntity>> =
        flow { emit(userService.updateUserInfo(ProfileInfoRequestDTO(username, email))) }
            .map { user ->
                val body = user.body ?: throw ApiNotRespondingException()
                body.toDomain()
            }
            .map {
                Data.content(it)
            }
            .onStart {
                emit(Data.loading())
            }
            .catch { emit(Data.error(it)) }

    override fun logout(): Flow<Data<ResultEntity>> =
        flow { emit(userService.logout()) }
            .map { result ->
                result.toDomain()
            }
            .map {
                Data.content(it)
            }
            .onStart {
                emit(Data.loading())
            }
            .catch { emit(Data.error(it)) }
}

private fun InfoDTO.toDomain(): ResultEntity =
    ResultEntity(this.error != null)

private fun UserWrapperDTO.toDomain(): UserEntity =
    UserEntity(this.user.id, this.user.avatar, this.user.email, this.user.nickname)


