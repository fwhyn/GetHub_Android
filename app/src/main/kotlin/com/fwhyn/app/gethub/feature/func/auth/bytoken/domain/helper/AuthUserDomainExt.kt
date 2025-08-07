package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain

fun AuthUserDomain.toData(): AuthUserData {
    return AuthUserData(
        login = this.login,
        id = this.id
    )
}