package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import javax.inject.Inject

class GitHubUsersRepositoryReal @Inject constructor(
    private val getGitHubUsersRemoteDataSource: GitHubUsersRemoteDataSource,
) : GitHubUsersRepository() {
    override suspend fun onRunning(
        param: GetGitHubUsersParam,
        result: suspend (List<GitHubUserData>) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

}