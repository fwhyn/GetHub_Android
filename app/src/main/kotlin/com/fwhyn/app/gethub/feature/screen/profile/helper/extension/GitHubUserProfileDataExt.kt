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
        htmlUrl = this.htmlUrl,
        followersUrl = this.followersUrl,
        followingUrl = this.followingUrl,
        gistsUrl = this.gistsUrl,
        starredUrl = this.starredUrl,
        subscriptionsUrl = this.subscriptionsUrl,
        organizationsUrl = this.organizationsUrl,
        reposUrl = this.reposUrl,
        eventsUrl = this.eventsUrl,
        receivedEventsUrl = this.receivedEventsUrl,
        type = this.type,
        userViewType = this.userViewType,
        siteAdmin = this.siteAdmin,
        company = this.company,
        blog = this.blog,
        location = this.location,
        email = this.email,
        hireable = this.hireable,
        name = this.name,
        bio = this.bio,
        twitterUsername = this.twitterUsername,
        publicRepos = this.publicRepos,
        publicGists = this.publicGists,
        followers = this.followers,
        following = this.following,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun List<GitHubUserProfileData>.toUi(): List<GitHubUserProfileUi> {
    return this.map { it.toUi() }
}

fun Flow<List<GitHubUserProfileData>>.toUi(): Flow<List<GitHubUserProfileUi>> {
    return this.map { kmcList -> kmcList.toUi() }
}