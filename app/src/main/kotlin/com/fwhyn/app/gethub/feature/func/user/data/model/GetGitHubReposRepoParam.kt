package com.fwhyn.app.gethub.feature.func.user.data.model

class GetGitHubReposRepoParam(
    val username: String,
    val perPage: Int,
) {
    companion object {
        fun default(
            username: String,
            perPage: Int = 10,
        ) = GetGitHubReposRepoParam(
            username = username,
            perPage = perPage,
        )
    }
}