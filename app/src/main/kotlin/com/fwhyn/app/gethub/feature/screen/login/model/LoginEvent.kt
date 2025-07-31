package com.fwhyn.app.gethub.feature.screen.login.model

import com.fwhyn.app.gethub.feature.screen.login.component.LoginMessageCode

sealed class LoginEvent {
    data class OnNotify(val code: LoginMessageCode) : LoginEvent()
    data object OnLoggedIn : LoginEvent()
}