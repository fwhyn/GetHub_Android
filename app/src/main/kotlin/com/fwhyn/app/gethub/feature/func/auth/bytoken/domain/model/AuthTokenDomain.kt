package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model

data class AuthTokenDomain(
    val value: String,
) {
    companion object {
        fun default(
            value: String = "",
        ): AuthTokenDomain = AuthTokenDomain(
            value = value
        )
    }
}