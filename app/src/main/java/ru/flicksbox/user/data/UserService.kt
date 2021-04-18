package ru.flicksbox.user.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import ru.flicksbox.network.BaseDTO

interface UserService {

    @GET("user/profile")
    suspend fun getUser(@Header("cookie") cookie: String): BaseDTO<UserWrapperDTO>
}