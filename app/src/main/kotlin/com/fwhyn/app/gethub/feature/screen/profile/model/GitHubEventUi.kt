package com.fwhyn.app.gethub.feature.screen.profile.model

data class GitHubEventUi(
    val id: String,
    val type: String,
    val repoName: String,
    val branch: String,
    val commitNo: String,
    val author: String,
    val commitMessage: String,
    val createdAt: String,
) {
    companion object {
        fun default(
            id: String = "",
            type: String = "",
            repoName: String = "",
            branch: String = "",
            commitNo: String = "",
            author: String = "",
            commitMessage: String = "",
            createdAt: String = "",
        ): GitHubEventUi {
            return GitHubEventUi(
                id = id,
                type = type,
                repoName = repoName,
                branch = branch,
                commitNo = commitNo,
                author = author,
                commitMessage = commitMessage,
                createdAt = createdAt
            )
        }
    }
}

val gitHubEventUiFake = GitHubEventUi(
    id = "1234567890",
    type = "PushEvent",
    repoName = "fwhyn/app-gethub",
    branch = "main",
    commitNo = "abc123def456",
    author = "fwhyn",
    commitMessage = "Initial commit",
    createdAt = "2023-10-01T12:00:00Z"
)

val gitHubEventsUiFake = listOf(
    gitHubEventUiFake,
    gitHubEventUiFake.copy(
        id = "0987654321",
        type = "PullRequestEvent",
        branch = "feature/new-feature",
        commitNo = "def456abc123",
        author = "fwhyn",
        commitMessage = "Add new feature",
        createdAt = "2023-10-02T14:30:00Z"
    ),
    gitHubEventUiFake.copy(
        id = "1122334455",
        type = "IssueCommentEvent",
        branch = "bugfix/issue-123",
        commitNo = "ghi789jkl012",
        author = "fwhyn",
        commitMessage = "Fix issue #123",
        createdAt = "2023-10-03T16:45:00Z"
    )
)
