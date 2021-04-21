package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class SignupBody(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("repeated_password") val repeated_password: String,
)