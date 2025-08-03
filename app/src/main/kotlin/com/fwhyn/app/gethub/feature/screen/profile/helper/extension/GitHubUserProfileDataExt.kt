package com.fwhyn.app.gethub.feature.screen.profile.helper.extension

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserProfileData
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubUserProfileUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun GitHubUserProfileData.toUi(): GitHubUserProfileUi {
    return GitHubUserProfileUi(
        login = this.login,
        id = this.id,
        nodeId = this.nodeId,
        avatarUrl = this.avatarUrl,
        gravatarId = this.gravatarId,
        url = this.url,
        htmlUrl = this.htmlUrl
    )
}

fun List<GitHubUserProfileData>.toUi(): List<GitHubUserProfileUi> {
    return this.map { it.toUi() }
}

fun Flow<List<GitHubUserProfileData>>.toUi(): Flow<List<GitHubUserProfileUi>> {
    return this.map { kmcList -> kmcList.toUi() }
}