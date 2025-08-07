package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData

interface AuthTokenLocalDataSource {
    var token: AuthTokenData?
}