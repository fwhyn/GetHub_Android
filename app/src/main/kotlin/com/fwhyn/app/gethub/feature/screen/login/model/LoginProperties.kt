package com.fwhyn.app.gethub.feature.screen.login.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginProperties(
    val event: SharedFlow<LoginEvent>,
    val state: StateFlow<LoginState>,
    val email: StateFlow<String>,
    val password: StateFlow<String>,
    val isRememberMe: StateFlow<Boolean>,
) {

    companion object {
        fun default(
            event: SharedFlow<LoginEvent> = MutableSharedFlow(),
            state: StateFlow<LoginState> = MutableStateFlow(LoginState.Idle),
            email: StateFlow<String> = MutableStateFlow(""),
            password: StateFlow<String> = MutableStateFlow(""),
            isRememberMe: StateFlow<Boolean> = MutableStateFlow(false),
        ): LoginProperties {
            return LoginProperties(
                event = event,
                state = state,
                email = email,
                password = password,
                isRememberMe = isRememberMe,
            )
        }
    }

    val isValid: StateFlow<Boolean> = MutableStateFlow(true)
    var tryCount: Int = 0
}

val loginPropertiesFake = LoginProperties.default()