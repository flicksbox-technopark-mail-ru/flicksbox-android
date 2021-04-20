package ru.flicksbox.user.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.flicksbox.network.BaseDTO

interface UserService {

    @GET("user/profile")
    suspend fun getUser(): BaseDTO<UserWrapperDTO>

    @POST("session")
    suspend fun login(@Body login: LoginBody): BaseDTO<UserWrapperDTO>
}