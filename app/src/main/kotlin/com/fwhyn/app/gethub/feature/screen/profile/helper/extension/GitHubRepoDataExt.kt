package com.fwhyn.app.gethub.feature.screen.profile.helper.extension

import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubRepoData
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubRepoUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun GitHubRepoData.toUi(): GitHubRepoUi {
    return GitHubRepoUi(
        id = this.id,
        name = this.name,
        fullName = this.fullName,
        htmlUrl = this.htmlUrl,
        description = this.description,
        url = this.url,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        pushedAt = this.pushedAt,
        homepage = this.homepage,
        size = this.size,
        stargazersCount = this.stargazersCount,
        watchersCount = this.watchersCount,
        language = this.language,
        forksCount = this.forksCount,
        archived = this.archived,
        disabled = this.disabled,
        openIssuesCount = this.openIssuesCount,
        license = this.license,
        topics = this.topics,
        visibility = this.visibility,
        forks = this.forks,
        openIssues = this.openIssues,
        watchers = this.watchers,
        defaultBranch = this.defaultBranch
    )
}

fun List<GitHubRepoData>.toUi(): List<GitHubRepoUi> {
    return this.map { it.toUi() }
}

fun Flow<List<GitHubRepoData>>.toUi(): Flow<List<GitHubRepoUi>> {
    return this.map { repoList -> repoList.toUi() }
}