package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubReposRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubRepoData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
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
            val data = response.body()
            if (data == null) throw Exzeption(Status.NotFound)
            if (data.isEmpty()) throw Exzeption(StatusExt.EmptyResult)

            loadedData.addAll(data)
            pageToLoad++

            result(loadedData.toList())
        } else {
            throw throw Exzeption(
                status = Status.ReadError,
                throwable = Throwable("Error fetching repositories: ${response.errorBody()?.string()}")
            )
        }
    }

}