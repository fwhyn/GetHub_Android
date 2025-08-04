package com.fwhyn.app.gethub.feature.func.event.data.repository

import com.fwhyn.app.gethub.feature.func.event.data.model.GetGitHubEventsRepoParam
import com.fwhyn.app.gethub.feature.func.event.data.model.GitHubEventData
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class GetGitHubEventsRepositoryMain @Inject constructor(
    private val gitHubEventsRemoteDataSource: GitHubEventsRemoteDataSource
) : GetGitHubEventsRepository() {

    private val loadedData: MutableSet<GitHubEventData> = mutableSetOf()
    private var pageToLoad: Int = 1

    override suspend fun onRunning(
        param: GetGitHubEventsRepoParam,
        result: suspend (List<GitHubEventData>) -> Unit
    ) {
        val response: Response<List<GitHubEventData>> = gitHubEventsRemoteDataSource.getEvents(
            username = param.username,
            perPage = param.perPage,
            page = pageToLoad
        )

        if (response.isSuccessful) {
            val data = response.body() ?: throw Exception("No data found")
            loadedData.addAll(data)
            pageToLoad++

            result(loadedData.toList())
        } else {
            throw Exception("Error fetching repositories: ${response.errorBody()?.string()}")
        }
    }

}