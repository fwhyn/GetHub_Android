package com.fwhyn.app.gethub.feature.screen.profile.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileProperties(
    val event: SharedFlow<ProfileEvent>,
    val gitHubUserProfile: StateFlow<GitHubUserProfileUi>,
    val gitHubRepos: StateFlow<List<GitHubRepoUi>>,
    val gitHubEvents: StateFlow<List<GitHubEventUi>>,
) {
    companion object {
        fun default(
            event: SharedFlow<ProfileEvent> = MutableSharedFlow(),
            gitHubUserProfile: StateFlow<GitHubUserProfileUi> = MutableStateFlow(GitHubUserProfileUi.default()),
            gitHubRepos: StateFlow<List<GitHubRepoUi>> = MutableStateFlow(emptyList()),
            gitHubEvents: StateFlow<List<GitHubEventUi>> = MutableStateFlow(emptyList()),
        ): ProfileProperties {
            return ProfileProperties(
                event = event,
                gitHubUserProfile = gitHubUserProfile,
                gitHubRepos = gitHubRepos,
                gitHubEvents = gitHubEvents,
            )
        }
    }

    var lastFetchEventsIsEmpty = false
}

val profilePropertiesFake = ProfileProperties.default(
    gitHubUserProfile = MutableStateFlow(gitHubUserProfileUiFake),
    gitHubRepos = MutableStateFlow(gitHubReposUiFake),
    gitHubEvents = MutableStateFlow(gitHubEventsUiFake),
)