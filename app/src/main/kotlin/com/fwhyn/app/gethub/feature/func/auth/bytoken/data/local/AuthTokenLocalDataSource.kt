package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import kotlinx.coroutines.flow.Flow

interface AuthTokenLocalDataSource {
    suspend fun get(): AuthTokenData?
    fun getFlow(): Flow<AuthTokenData?>
    suspend fun set(data: AuthTokenData?)
}