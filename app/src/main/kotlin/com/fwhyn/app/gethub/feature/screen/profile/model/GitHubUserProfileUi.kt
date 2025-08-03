package com.fwhyn.app.gethub.feature.screen.profile.model

data class GitHubUserProfileUi(
    val login: String,
    val id: Int,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String,
) {
    companion object {
        fun default(
            login: String = "",
            id: Int = 0,
            nodeId: String = "",
            avatarUrl: String = "",
            gravatarId: String = "",
            url: String = "",
            htmlUrl: String = "",
        ): GitHubUserProfileUi {
            return GitHubUserProfileUi(
                login = login,
                id = id,
                nodeId = nodeId,
                avatarUrl = avatarUrl,
                gravatarId = gravatarId,
                url = url,
                htmlUrl = htmlUrl
            )
        }
    }
}

val gitHubUserUiFake = GitHubUserProfileUi(
    login = "fwhyn",
    id = 1,
    nodeId = "MDQ6VXNlcjE=",
    avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
    gravatarId = "",
    url = "https://api.github.com/users/fwhyn",
    htmlUrl = "https://github.com/fwhyn",
)

val gitHubUsersUiFake = listOf(
    gitHubUserUiFake,
    GitHubUserProfileUi(
        login = "mojombo",
        id = 3,
        nodeId = "MDQ6VXNlcjM=",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/mojombo",
        htmlUrl = "https://github.com/mojombo",
    ),
    GitHubUserProfileUi(
        login = "defunkt",
        id = 2,
        nodeId = "MDQ6VXNlcjI=",
        avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/defunkt",
        htmlUrl = "https://github.com/defunkt",
    ),
    GitHubUserProfileUi(
        login = "pjhyett",
        id = 1,
        nodeId = "MDQ6VXNlcjE=",
        avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
        gravatarId = "",
        url = "https://api.github.com/users/pjhyett",
        htmlUrl = "https://github.com/pjhyett",
    )
)