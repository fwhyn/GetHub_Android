package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubUsersRemoteDataSource {
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") perPage: Int,
        @Query("since") since: Int = 0,
    ): Response<List<GitHubUserData>>
}