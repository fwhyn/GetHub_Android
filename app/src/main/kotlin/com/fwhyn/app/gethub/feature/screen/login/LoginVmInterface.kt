package com.fwhyn.app.gethub.feature.screen.login

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties

abstract class LoginVmInterface : ViewModel() {

    abstract val properties: LoginProperties

    open fun onEmailChanged(value: String) {}
    open fun onPasswordChanged(value: String) {}
    open fun onRememberMeChecked() {}
    open fun onLogin() {}
}