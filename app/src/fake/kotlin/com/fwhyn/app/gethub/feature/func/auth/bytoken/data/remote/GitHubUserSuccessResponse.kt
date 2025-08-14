package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

object GitHubUserSuccessResponse {
    const val LOGIN = "fwhyn"
    const val ID = 22416802

    val success = """
        {
            "login": "$LOGIN",
            "id": $ID,
            "node_id": "MDQ6VXNlcjIyNDE2ODAy",
            "avatar_url": "https://avatars.githubusercontent.com/u/22416802?v=4",
            "gravatar_id": "",
            "url": "https://api.github.com/users/fwhyn",
            "html_url": "https://github.com/fwhyn",
            "followers_url": "https://api.github.com/users/fwhyn/followers",
            "following_url": "https://api.github.com/users/fwhyn/following{/other_user}",
            "gists_url": "https://api.github.com/users/fwhyn/gists{/gist_id}",
            "starred_url": "https://api.github.com/users/fwhyn/starred{/owner}{/repo}",
            "subscriptions_url": "https://api.github.com/users/fwhyn/subscriptions",
            "organizations_url": "https://api.github.com/users/fwhyn/orgs",
            "repos_url": "https://api.github.com/users/fwhyn/repos",
            "events_url": "https://api.github.com/users/fwhyn/events{/privacy}",
            "received_events_url": "https://api.github.com/users/fwhyn/received_events",
            "type": "User",
            "user_view_type": "public",
            "site_admin": false,
            "name": "fwhyn",
            "company": null,
            "blog": "",
            "location": "+62",
            "email": "fwhynseventh@gmail.com",
            "hireable": true,
            "bio": "Android Engineer",
            "twitter_username": "yana_wahyuna",
            "notification_email": "fwhynseventh@gmail.com",
            "public_repos": 20,
            "public_gists": 0,
            "followers": 4,
            "following": 6,
            "created_at": "2016-09-24T16:18:58Z",
            "updated_at": "2025-08-01T04:31:59Z"
        }
    """.trimIndent()
}