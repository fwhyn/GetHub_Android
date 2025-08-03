package com.fwhyn.app.gethub.feature.screen.profile.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileProperties(
    val event: SharedFlow<ProfileEvent>,
    val state: StateFlow<ProfileState>,
    val gitHubUserProfile: StateFlow<GitHubUserProfileUi>,
) {
    companion object {
        fun default(
            event: SharedFlow<ProfileEvent> = MutableSharedFlow(),
            state: StateFlow<ProfileState> = MutableStateFlow(ProfileState.Idle),
            gitHubUserProfile: StateFlow<GitHubUserProfileUi> = MutableStateFlow(GitHubUserProfileUi.default()),
        ): ProfileProperties {
            return ProfileProperties(
                event = event,
                state = state,
                gitHubUserProfile = gitHubUserProfile
            )
        }
    }
}

val profilePropertiesFake = ProfileProperties.default()