package ru.flicksbox.user.data

import com.google.gson.annotations.SerializedName

data class ProfilePasswordRequestDTO(
    @SerializedName("old_password") val old_password: String,
    @SerializedName("new_password") val new_password: String,
    @SerializedName("repeated_new_password") val repeated_new_password: String,
)