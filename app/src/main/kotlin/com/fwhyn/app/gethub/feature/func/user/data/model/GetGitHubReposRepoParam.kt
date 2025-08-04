package com.fwhyn.app.gethub.feature.func.user.data.model

class GetGitHubReposRepoParam(
    val username: String,
    val perPage: Int,
) {
    companion object {
        fun default(
            username: String,
            perPage: Int = 20,
        ) = GetGitHubReposRepoParam(
            username = username,
            perPage = perPage,
        )
    }
}