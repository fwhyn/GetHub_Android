package com.fwhyn.app.gethub.feature.screen.login

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.lib.baze.compose.model.CommonProperties

abstract class LoginVmInterface : ViewModel() {

    abstract val commonProp: CommonProperties
    abstract val properties: LoginProperties

    open fun onPasswordChanged(value: String) {}
    open fun onLogin() {}
}