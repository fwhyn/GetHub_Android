package com.fwhyn.app.gethub.feature.screen.home.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class HomeProperties(
    val event: SharedFlow<HomeEvent>,
    val state: StateFlow<HomeState>,
    val gitHubUsers: StateFlow<List<GitHubUserUi>>,
    val querySuggestions: StateFlow<List<GitHubUserUi>>,
    val query: StateFlow<String>,
) {
    companion object {
        fun default(
            event: SharedFlow<HomeEvent> = MutableSharedFlow(),
            state: StateFlow<HomeState> = MutableStateFlow(HomeState.Idle),
            gitHubUsers: StateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList()),
            querySuggestions: StateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList()),
            query: StateFlow<String> = MutableStateFlow(""),
        ): HomeProperties {
            return HomeProperties(
                event = event,
                state = state,
                gitHubUsers = gitHubUsers,
                querySuggestions = querySuggestions,
                query = query
            )
        }
    }
}

val homePropertiesFake = HomeProperties.default(
    gitHubUsers = MutableStateFlow(gitHubUsersUiFake)
)