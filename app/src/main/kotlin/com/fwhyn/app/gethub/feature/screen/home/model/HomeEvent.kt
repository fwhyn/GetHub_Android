package com.fwhyn.app.gethub.feature.screen.home.model

import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode

sealed class HomeEvent {
    data class Notify(val code: HomeMessageCode) : HomeEvent()
    data class GoToProfile(val user: String) : HomeEvent()
}