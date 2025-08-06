package com.fwhyn.app.gethub.feature.screen.home.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeProperties(
    val event: SharedFlow<HomeEvent>,
    val state: StateFlow<HomeState>,
    val gitHubUsers: StateFlow<List<GitHubUserUi>>,
) {
    companion object {
        fun default(
            event: SharedFlow<HomeEvent> = MutableSharedFlow(),
            state: StateFlow<HomeState> = MutableStateFlow(HomeState.Idle),
            gitHubUsers: StateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList()),
        ): HomeProperties {
            return HomeProperties(
                event = event,
                state = state,
                gitHubUsers = gitHubUsers,
            )
        }
    }
}

val homePropertiesFake = HomeProperties.default()