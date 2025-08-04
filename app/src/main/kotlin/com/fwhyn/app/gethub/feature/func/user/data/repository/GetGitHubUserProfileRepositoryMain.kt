package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUserProfileRemoteDataSource
import javax.inject.Inject

class GetGitHubUserProfileRepositoryMain @Inject constructor(
    private val gitHubUserProfileRemoteDataSource: GitHubUserProfileRemoteDataSource,
) : GetGitHubUserProfileRepository() {
    override suspend fun onRunning(
        param: GetGitHubUserProfileRepoParam,
        result: suspend (GitHubUserProfileData) -> Unit,
    ) {
        val response = gitHubUserProfileRemoteDataSource.getUserProfile(
            username = param.username,
        )
        val data = response.body() ?: throw Exception("Failed to fetch user profile data")

        result(data)
    }

}