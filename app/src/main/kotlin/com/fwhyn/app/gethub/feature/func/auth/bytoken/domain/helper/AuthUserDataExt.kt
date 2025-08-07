package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain

fun AuthUserData.toDomain(): AuthUserDomain {
    return AuthUserDomain(
        login = this.login,
        id = this.id
    )
}