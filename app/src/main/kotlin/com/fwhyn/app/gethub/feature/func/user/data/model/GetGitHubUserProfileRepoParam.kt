package com.fwhyn.app.gethub.feature.func.user.data.model

data class GetGitHubUserProfileRepoParam(
    val username: String,
) {
    companion object {
        fun default(
            username: String = "",
        ): GetGitHubUserProfileRepoParam {
            return GetGitHubUserProfileRepoParam(username)
        }
    }
}