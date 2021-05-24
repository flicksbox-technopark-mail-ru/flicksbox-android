package ru.flicksbox.user.data

import retrofit2.http.*
import ru.flicksbox.network.BaseDTO

interface UserService {

    @GET("user/profile")
    suspend fun getUser(): BaseDTO<UserWrapperDTO>

    @POST("session")
    suspend fun login(@Body login: LoginRequestDTO): BaseDTO<UserWrapperDTO>

    @POST("user/register")
    suspend fun signup(@Body signup: SignupRequestDTO): BaseDTO<UserWrapperDTO>

    @PUT("user/profile")
    suspend fun updateUserInfo(@Body info: ProfileInfoRequestDTO): BaseDTO<UserWrapperDTO>

    @PUT("user/password")
    suspend fun updatePassword(@Body password: ProfilePasswordRequestDTO): BaseDTO<UserWrapperDTO>

    @DELETE("session")
    suspend fun logout(): InfoDTO
}