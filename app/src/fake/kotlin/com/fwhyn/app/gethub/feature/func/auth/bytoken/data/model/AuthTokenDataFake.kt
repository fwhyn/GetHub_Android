package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE


val unvalidatedAuthTokenDataFake = AuthTokenData(
    value = TOKEN_FAKE,
    validatedUser = null
)

val authTokenDataFake = unvalidatedAuthTokenDataFake.copy(validatedUser = authUserDataFake)