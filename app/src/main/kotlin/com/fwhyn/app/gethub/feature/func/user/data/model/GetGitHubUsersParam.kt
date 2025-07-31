package com.fwhyn.app.gethub.feature.func.user.data.model

data class GetGitHubUsersParam(
    val perPage: Int = 10,
    val page: Page = Page.Next,
) {
    enum class Page {
        Prev,
        Next
    }
}