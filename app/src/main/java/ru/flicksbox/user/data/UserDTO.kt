package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class UserWrapperDTO(
    @SerializedName("user") var user: UserDTO,
)

data class UserDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
)