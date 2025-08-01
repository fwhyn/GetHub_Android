package com.fwhyn.app.gethub.feature.func.user.data.model

data class GetGitHubUsersParam(
    val perPage: Int,
) {
    companion object {
        fun default(
            perPage: Int = 20,
        ) = GetGitHubUsersParam(
            perPage = perPage,
        )
    }
}