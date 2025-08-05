package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUserProfileRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
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

        if (response.isSuccessful) {
            val data = response.body() ?: throw Exzeption(Status.NotFound)
            result(data)
        } else {
            throw Exzeption(
                status = Status.ReadError,
                throwable = Throwable("Error fetching user profile: ${response.errorBody()?.string()}")
            )
        }
    }

}