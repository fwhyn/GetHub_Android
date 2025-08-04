package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubRepoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubReposRemoteDataSource {
    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int = 1,
    ): Response<List<GitHubRepoData>>
}