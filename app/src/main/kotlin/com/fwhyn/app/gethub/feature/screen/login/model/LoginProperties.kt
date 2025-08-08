package com.fwhyn.app.gethub.feature.screen.login.model

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class LoginProperties(
    val event: SharedFlow<LoginEvent>,
    val state: StateFlow<LoginState>,
    val password: StateFlow<String>,
    val isValid: StateFlow<Boolean>,
) {
    companion object {
        fun default(
            event: SharedFlow<LoginEvent> = MutableSharedFlow(),
            state: StateFlow<LoginState> = MutableStateFlow(LoginState.Idle),
            password: StateFlow<String> = MutableStateFlow(""),
            isValid: StateFlow<Boolean> = MutableStateFlow(false),
        ): LoginProperties {
            return LoginProperties(
                event = event,
                state = state,
                password = password,
                isValid = isValid
            )
        }
    }

    var isFirstLogin: Boolean = true
    var callerRoute: String = ""
}

val loginPropertiesFake = LoginProperties.default()