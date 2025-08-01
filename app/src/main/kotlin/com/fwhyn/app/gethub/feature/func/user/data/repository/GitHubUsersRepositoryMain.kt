package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import javax.inject.Inject

class GitHubUsersRepositoryMain @Inject constructor(
    private val gitHubUsersRemoteDataSource: GitHubUsersRemoteDataSource,
) : GitHubUsersRepository() {

    private var lastId = 0
    private var loadedUsers = mutableSetOf<GitHubUserData>()

    override suspend fun onRunning(
        param: GetGitHubUsersParam,
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