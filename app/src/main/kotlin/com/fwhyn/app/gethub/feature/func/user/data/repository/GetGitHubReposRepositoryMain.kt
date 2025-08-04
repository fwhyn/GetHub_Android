package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubReposRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubRepoData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class GetGitHubReposRepositoryMain @Inject constructor(
    private val gitHubReposRemoteDataSource: GitHubReposRemoteDataSource
) : GetGitHubReposRepository() {

    private val loadedData: MutableSet<GitHubRepoData> = mutableSetOf()
    private var pageToLoad: Int = 1

    override suspend fun onRunning(
        param: GetGitHubReposRepoParam,
        result: suspend (List<GitHubRepoData>) -> Unit
    ) {
        val response: Response<List<GitHubRepoData>> = gitHubReposRemoteDataSource.getRepos(
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