package com.fwhyn.app.gethub.feature.screen.profile.model

import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode

sealed class ProfileEvent {
    data class Notify(val code: HomeMessageCode) : ProfileEvent()
    data class GoToHome(val user: String) : ProfileEvent()
}