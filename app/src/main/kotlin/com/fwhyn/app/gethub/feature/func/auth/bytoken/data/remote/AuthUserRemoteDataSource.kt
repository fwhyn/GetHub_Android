package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import retrofit2.Response
import retrofit2.http.GET

interface AuthUserRemoteDataSource {
    @GET("user")
    suspend fun getUser(): Response<AuthUserData>
}