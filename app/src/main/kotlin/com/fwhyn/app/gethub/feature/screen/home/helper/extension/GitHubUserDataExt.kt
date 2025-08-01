package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun GitHubUserData.toUi(): GitHubUserUi {
    return GitHubUserUi(
        login = this.login,
        id = this.id,
        nodeId = this.nodeId,
        avatarUrl = this.avatarUrl,
        gravatarId = this.gravatarId,
        url = this.url,
        htmlUrl = this.htmlUrl
    )
}

fun List<GitHubUserData>.toUi(): List<GitHubUserUi> {
    return this.map { it.toUi() }
}

fun Flow<List<GitHubUserData>>.toUi(): Flow<List<GitHubUserUi>> {
    return this.map { kmcList -> kmcList.toUi() }
}