package com.fwhyn.app.gethub.feature.func.event.data.model

data class GetGitHubEventsRepoParam(
    val perPage: Int,
) {
    companion object {
        fun default(
            perPage: Int = 20,
        ) = GetGitHubEventsRepoParam(
            perPage = perPage,
        )
    }
}