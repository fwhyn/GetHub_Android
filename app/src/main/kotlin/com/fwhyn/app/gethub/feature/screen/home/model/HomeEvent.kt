package com.fwhyn.app.gethub.feature.screen.home.model

import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.helper.OpenSafCode

sealed class HomeEvent {
    data class OnNotify(val code: HomeMessageCode) : HomeEvent()
    data class OpenSaf(val code: OpenSafCode) : HomeEvent()
}