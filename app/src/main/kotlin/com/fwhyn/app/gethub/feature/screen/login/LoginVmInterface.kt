package com.fwhyn.app.gethub.feature.screen.login

import androidx.lifecycle.ViewModel

abstract class LoginVmInterface : ViewModel() {

//    abstract val loginUiState: LoginUiState

    open fun onEmailValueChange(value: String) {}
    open fun onPasswordValueChange(value: String) {}
    open fun onCheckRememberMe() {}

    //    open fun onLogin(getAuthTokenParam: GetAuthTokenParam) {}
    open fun onCalledFromBackStack() {}
}