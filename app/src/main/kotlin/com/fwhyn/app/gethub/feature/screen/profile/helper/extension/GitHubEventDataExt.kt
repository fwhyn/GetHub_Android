package com.fwhyn.app.gethub.feature.screen.profile.helper.extension

import com.fwhyn.app.gethub.feature.func.event.data.model.GitHubEventData
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubEventUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun GitHubEventData.toUi(): GitHubEventUi {
    return GitHubEventUi(
        id = id,
        type = type,
        repoName = repo.name,
        createdAt = createdAt,
    )
}

fun List<GitHubEventData>.toUi(): List<GitHubEventUi> {
    return map { it.toUi() }
}

fun Flow<List<GitHubEventData>>.toUi(): Flow<List<GitHubEventUi>> {
    return map { eventList -> eventList.toUi() }
}
