package com.fwhyn.app.gethub.feature.func.event.data.model

data class GetGitHubEventsRepoParam(
    val username: String,
    val perPage: Int,
) {
    companion object {
        fun default(
            username: String,
            perPage: Int = 20,
        ) = GetGitHubEventsRepoParam(
            username = username,
            perPage = perPage,
        )
    }
}