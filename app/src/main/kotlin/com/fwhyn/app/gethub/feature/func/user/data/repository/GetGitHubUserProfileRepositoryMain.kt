package com.fwhyn.app.gethub.feature.func.user.data.repository

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUserProfileRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUserProfileRemoteDataSource
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import javax.inject.Inject

class GetGitHubUserProfileRepositoryMain @Inject constructor(
    private val gitHubUserProfileRemoteDataSource: GitHubUserProfileRemoteDataSource,
) : GetGitHubUserProfileRepository() {

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

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
            when (response.code()) {
                401 -> throw Exzeption(Status.Unauthorized)
                else -> throw Exzeption(
                    status = Status.ReadError,
                    throwable = Throwable("Error fetching user profile: ${response.errorBody()?.string()}")
                )
            }
        }
    }

}