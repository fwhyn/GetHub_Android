package com.fwhyn.app.gethub.feature.screen.home.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow

data class HomeParam(
    val event: SharedFlow<HomeEvent>,
    val state: StateFlow<HomeState>,
    val isRealTimeData: StateFlow<Boolean>,
    val kmcUiList: StateFlow<List<KmcUi>>,
) {
    companion object {
        val started by lazy { WhileSubscribed(5000) }

        fun default(
            event: SharedFlow<HomeEvent> = MutableSharedFlow(),
            state: StateFlow<HomeState> = MutableStateFlow(HomeState.Idle),
            isRealTimeData: StateFlow<Boolean> = MutableStateFlow(false),
            kmcUiList: StateFlow<List<KmcUi>> = MutableStateFlow(emptyList()),
        ): HomeParam {
            return HomeParam(
                event = event,
                state = state,
                isRealTimeData = isRealTimeData,
                kmcUiList = kmcUiList
            )
        }
    }
}

val homeParamFake = HomeParam.default(
    kmcUiList = MutableStateFlow(kmcUiListFake)
)