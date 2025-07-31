package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import javax.inject.Inject

class GitHubUsersRemoteDataSourceReal @Inject constructor(
    private val gitHubUsersApi: GitHubUsersApi,
) : GitHubUsersRemoteDataSource {

    private var lastId = 0

    override suspend fun get(param: GetGitHubUsersParam): List<GitHubUserData> {
        val response = gitHubUsersApi.getUsers(
            since = lastId
        )

        val users = response.body() ?: throw Exception("Failed to fetch users")
        lastId = users.lastOrNull()?.id ?: 0

        return users
    }

}