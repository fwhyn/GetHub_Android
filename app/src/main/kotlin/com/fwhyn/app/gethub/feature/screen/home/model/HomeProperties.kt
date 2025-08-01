package com.fwhyn.app.gethub.feature.screen.home.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow

data class HomeProperties(
    val event: SharedFlow<HomeEvent>,
    val state: StateFlow<HomeState>,
    val gitHubUsers: StateFlow<List<GitHubUserUi>>,
) {
    companion object {
        val started by lazy { WhileSubscribed(5000) }

        fun default(
            event: SharedFlow<HomeEvent> = MutableSharedFlow(),
            state: StateFlow<HomeState> = MutableStateFlow(HomeState.Idle),
            kmcUiList: StateFlow<List<GitHubUserUi>> = MutableStateFlow(emptyList()),
        ): HomeProperties {
            return HomeProperties(
                event = event,
                state = state,
                gitHubUsers = kmcUiList
            )
        }
    }
}

val homePropertiesFake = HomeProperties.default()