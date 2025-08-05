package com.fwhyn.app.gethub.feature.screen.profile.model

import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileMessageCode

sealed class ProfileEvent {
    data class Notify(val code: ProfileMessageCode) : ProfileEvent()
    data class GoToHome(val user: String) : ProfileEvent()
}