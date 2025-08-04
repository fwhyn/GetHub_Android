package com.fwhyn.app.gethub.feature.func.user.data.model

data class GetGitHubUsersRepoParam(
    val perPage: Int,
) {
    companion object {
        fun default(
            perPage: Int = 20,
        ) = GetGitHubUsersRepoParam(
            perPage = perPage,
        )
    }
}