package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class ProfileInfoRequestDTO(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("email") val email: String,
)