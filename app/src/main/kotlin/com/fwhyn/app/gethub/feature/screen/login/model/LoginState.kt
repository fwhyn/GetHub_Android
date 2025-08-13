package com.fwhyn.app.gethub.feature.screen.login.model

sealed class LoginState {
    data object Loading : LoginState()
}