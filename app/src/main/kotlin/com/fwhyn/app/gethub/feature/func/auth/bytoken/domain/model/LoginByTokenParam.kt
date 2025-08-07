package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model

data class LoginByTokenParam(
    val token: String?,
) {
    companion object {
        fun default(
            token: String? = null,
        ): LoginByTokenParam = LoginByTokenParam(
            token = token
        )
    }
}