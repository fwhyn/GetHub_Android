package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model

data class AuthTokenData(
    val value: String,
    val validatedUser: AuthUserData?,
) {
    companion object {
        fun default(
            value: String = "",
            validatedUser: AuthUserData? = null,
        ): AuthTokenData = AuthTokenData(
            value = value,
            validatedUser = validatedUser
        )
    }
}