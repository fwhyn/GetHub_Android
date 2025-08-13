package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.AuthUserRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import javax.inject.Inject

class AuthUserRepositoryMain @Inject constructor(
    private val authUserRemoteDataSource: AuthUserRemoteDataSource,
) : AuthUserRepository {
    override suspend fun get(param: Unit): AuthUserData {
        val response = authUserRemoteDataSource.getUser()
        if (response.isSuccessful) {
            return response.body() ?: throw Exzeption(Status.NotFound)
        } else {
            when (response.code()) {
                401 -> throw Exzeption(Status.Unauthorized)
                else -> throw Exception("Failed to fetch user data: ${response.errorBody()?.string()}")
            }
        }
    }
}