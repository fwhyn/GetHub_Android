package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE

val authTokenDataFake = AuthTokenData(
    value = TOKEN_FAKE,
    validatedUser = authUserDataFake
)

val unvalidatedAuthTokenDataFake = authTokenDataFake.copy(validatedUser = null)