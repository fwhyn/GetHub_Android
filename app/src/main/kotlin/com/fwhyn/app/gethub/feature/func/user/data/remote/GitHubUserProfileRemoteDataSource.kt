package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUserProfileRemoteDataSource {
    @GET("users/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String,
    ): Response<GitHubUserProfileData>
}