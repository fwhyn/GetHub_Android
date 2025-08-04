package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import javax.inject.Inject

class GetGitHubUsersRepositoryMain @Inject constructor(
    private val gitHubUsersRemoteDataSource: GitHubUsersRemoteDataSource,
) : GetGitHubUsersRepository() {

    private var lastId = 0
    private var loadedUsers = mutableSetOf<GitHubUserData>()

    override suspend fun onRunning(
        param: GetGitHubUsersRepoParam,
        result: suspend (List<GitHubUserData>) -> Unit,
    ) {
        val response = gitHubUsersRemoteDataSource.getUsers(
            perPage = param.perPage,
            since = lastId
        )
        val users = response.body() ?: throw Exception("Failed to fetch users")

        loadedUsers.addAll(users)
        lastId = loadedUsers.lastOrNull()?.id ?: 0

        result(loadedUsers.toList())
    }
}