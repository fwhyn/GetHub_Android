package com.fwhyn.app.gethub.feature.func.user.data.model

data class GetGitHubUsersParam(
    val perPage: Int,
) {
    companion object {
        fun default(
            perPage: Int = 10,
        ) = GetGitHubUsersParam(
            perPage = perPage,
        )
    }
}