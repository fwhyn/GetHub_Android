package com.fwhyn.app.gethub.feature.func.event.data.remote

import com.fwhyn.app.gethub.feature.func.event.data.model.GitHubEventData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubEventsRemoteDataSource {
    @GET("users/{username}/events")
    suspend fun getEvents(
        @Path("username") username: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int = 1,
    ): Response<List<GitHubEventData>>
}