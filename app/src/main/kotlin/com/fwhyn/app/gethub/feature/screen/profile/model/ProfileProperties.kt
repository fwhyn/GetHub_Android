package com.fwhyn.app.gethub.feature.screen.profile.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileProperties(
    val event: SharedFlow<ProfileEvent>,
    val state: StateFlow<ProfileState>,
    val gitHubUserProfile: StateFlow<GitHubUserProfileUi>,
    val gitHubRepos: StateFlow<List<GitHubRepoUi>>,
    val gitHubEvents: StateFlow<List<GitHubEventUi>>,
) {
    companion object {
        fun default(
            event: SharedFlow<ProfileEvent> = MutableSharedFlow(),
            state: StateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle),
            gitHubUserProfile: StateFlow<GitHubUserProfileUi> = MutableStateFlow(GitHubUserProfileUi.default()),
            gitHubRepos: StateFlow<List<GitHubRepoUi>> = MutableStateFlow(emptyList()),
            gitHubEvents: StateFlow<List<GitHubEventUi>> = MutableStateFlow(emptyList()),
        ): ProfileProperties {
            return ProfileProperties(
                event = event,
                state = state,
                gitHubUserProfile = gitHubUserProfile,
                gitHubRepos = gitHubRepos,
                gitHubEvents = gitHubEvents,
            )
        }
    }
}

val profilePropertiesFake = ProfileProperties.default(
    gitHubUserProfile = MutableStateFlow(gitHubUserProfileUiFake),
    gitHubRepos = MutableStateFlow(gitHubReposUiFake),
    gitHubEvents = MutableStateFlow(gitHubEventsUiFake),
)