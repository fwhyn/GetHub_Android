package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.authTokenDataFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toDomain

val authTokenDomainFake = authTokenDataFake.toDomain()