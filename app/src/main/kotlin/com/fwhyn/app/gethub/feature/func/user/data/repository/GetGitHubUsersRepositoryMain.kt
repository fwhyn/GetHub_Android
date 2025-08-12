package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import javax.inject.Inject

class GetGitHubUsersRepositoryMain @Inject constructor(
    private val gitHubUsersRemoteDataSource: GitHubUsersRemoteDataSource,
) : GetGitHubUsersRepository() {

    private var lastId = 0
    private var loadedUsers = mutableSetOf<GitHubUserData>()

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

    override suspend fun onRunning(
        param: GetGitHubUsersRepoParam,
        result: suspend (List<GitHubUserData>) -> Unit,
    ) {
        val response = gitHubUsersRemoteDataSource.getUsers(
            perPage = param.perPage,
            since = lastId
        )

        if (response.isSuccessful) {
            val users = response.body()
            if (users == null) throw Exzeption(Status.NotFound)
            if (users.isEmpty()) throw Exzeption(StatusExt.EmptyResult)

            loadedUsers.addAll(users)
            lastId = loadedUsers.lastOrNull()?.id ?: 0

            result(loadedUsers.toList())
        } else {
            when {
                (response.message().contains("API rate limit exceeded", ignoreCase = true)) ||
                        (response.code() == 401) -> throw Exzeption(Status.Unauthorized)

                else -> throw Exzeption(
                    status = Status.ReadError,
                    throwable = Throwable("Error fetching users: ${response.errorBody()?.string()}")
                )
            }
        }
    }
}