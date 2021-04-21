package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class SignupRequestDTO(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("repeated_password") val repeatedPassword: String,
)