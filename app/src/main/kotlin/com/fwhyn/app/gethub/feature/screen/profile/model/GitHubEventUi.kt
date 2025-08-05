package com.fwhyn.app.gethub.feature.screen.profile.model

data class GitHubEventUi(
    val id: String,
    val type: String,
    val repoName: String,
    val createdAt: String,
) {
    companion object {
        fun default(
            id: String = "",
            type: String = "",
            repoName: String = "",
            createdAt: String = "",
        ): GitHubEventUi {
            return GitHubEventUi(
                id = id,
                type = type,
                repoName = repoName,
                createdAt = createdAt
            )
        }
    }
}

val gitHubEventUiFake = GitHubEventUi(
    id = "1234567890",
    type = "PushEvent",
    repoName = "fwhyn/app-gethub",
    createdAt = "2023-10-01T12:00:00Z"
)

val gitHubEventsUiFake = listOf(
    gitHubEventUiFake,
    gitHubEventUiFake.copy(
        id = "0987654321",
        type = "PullRequestEvent",
        createdAt = "2023-10-02T14:30:00Z"
    ),
    gitHubEventUiFake.copy(
        id = "1122334455",
        type = "IssueCommentEvent",
        createdAt = "2023-10-03T16:45:00Z"
    )
)
