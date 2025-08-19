package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthTokenLocalDataSourceFake : AuthTokenLocalDataSource {
    private val authTokenData: MutableStateFlow<AuthTokenData?> = MutableStateFlow(null)
    val token: String
        get() = authTokenData.value?.value ?: ""

    override suspend fun get(): AuthTokenData? {
        return authTokenData.value
    }

    override fun getFlow(): Flow<AuthTokenData?> {
        return authTokenData
    }

    override suspend fun set(data: AuthTokenData?) {
        authTokenData.value = data
    }
}