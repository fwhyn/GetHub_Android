package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import javax.inject.Inject

class AuthTokenRepositoryMain @Inject constructor(
    private val authTokenLocalDataSource: AuthTokenLocalDataSource,
) : AuthTokenRepository {
    override suspend fun get(param: Unit): AuthTokenData {
        val data = authTokenLocalDataSource.get() ?: throw Exzeption(Status.NotFound)
        return data
    }

    override suspend fun set(param: Unit, data: AuthTokenData?) {
        authTokenLocalDataSource.set(data)
    }
}