package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model

import com.google.gson.annotations.SerializedName

data class AuthUserData(
    @SerializedName("login") val login: String,
    @SerializedName("id") val id: Int,
)