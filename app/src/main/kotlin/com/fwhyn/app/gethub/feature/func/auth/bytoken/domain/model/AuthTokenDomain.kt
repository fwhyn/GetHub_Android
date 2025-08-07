package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model

data class AuthTokenDomain(
    val value: String,
    val validatedUser: AuthUserDomain?,
) {
    companion object {
        fun default(
            value: String = "",
            validatedUser: AuthUserDomain? = null,
        ): AuthTokenDomain = AuthTokenDomain(
            value = value,
            validatedUser = validatedUser
        )
    }
}