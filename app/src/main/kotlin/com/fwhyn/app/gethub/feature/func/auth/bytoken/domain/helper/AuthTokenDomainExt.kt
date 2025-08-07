package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain

fun AuthTokenDomain.toData(): AuthTokenData {
    return AuthTokenData(
        value = this.value
    )
}