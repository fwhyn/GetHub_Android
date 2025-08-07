package com.fwhyn.app.gethub.feature.screen.login.model

import com.fwhyn.app.gethub.feature.screen.login.component.LoginMessageCode

sealed class LoginEvent {
    data class Notify(val code: LoginMessageCode) : LoginEvent()
    data object LoggedIn : LoginEvent()
}