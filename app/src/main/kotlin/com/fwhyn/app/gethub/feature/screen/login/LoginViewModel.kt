package com.fwhyn.app.gethub.feature.screen.login

import androidx.lifecycle.viewModelScope
import com.fwhyn.app.gethub.common.helper.trimSpaceTabEnter
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCase
import com.fwhyn.app.gethub.feature.screen.login.component.LoginMessageCode
import com.fwhyn.app.gethub.feature.screen.login.model.LoginEvent
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginByTokenUseCase: LoginByTokenUseCase,
) : LoginVmInterface() {

    private val scope: CoroutineScope
        get() = viewModelScope

    private val event: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    private val state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    private val password: MutableStateFlow<String> = MutableStateFlow("")
    private val isValid: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override val properties: LoginProperties = LoginProperties(
        event = event,
        state = state,
        password = password,
        isValid = isValid,
    )

    // ----------------------------------------------------------------
    init {
        loginByToken()
    }

    override fun onPasswordChanged(value: String) {
        val trimmedValue = value.trimSpaceTabEnter()
        password.value = trimmedValue
        isValid.value = trimmedValue.isNotBlank()
    }

    override fun onLogin() {
        val param = LoginByTokenParam(token = password.value)
        loginByToken(param)
    }

    // ----------------------------------------------------------------
    private fun loginByToken(param: LoginByTokenParam = LoginByTokenParam.default()) {
        loginByTokenUseCase.invoke(
            scope = scope,
            onStart = { state.value = LoginState.Loading },
            onFetchParam = { param },
            onOmitResult = {
                it.onSuccess {
                    event.emit(LoginEvent.LoggedIn)
                }.onFailure {
                    if (properties.isFirstLogin) {
                        properties.isFirstLogin = false
                    } else {
                        handleError(it)
                    }
                }
            },
            onFinish = { state.value = LoginState.Idle },
        )
    }

    private suspend fun handleError(error: Throwable) {
        val code = when (error) {
            is Exzeption -> handleExzeptionError(error.status)
            is SocketTimeoutException -> LoginMessageCode.TimeOutError
            is UnknownHostException -> LoginMessageCode.NetworkError
            else -> LoginMessageCode.LoginError
        }

        event.emit(LoginEvent.Notify(code))
    }

    private fun handleExzeptionError(status: Status): LoginMessageCode {
        return when (status) {
            Status.Unauthorized -> LoginMessageCode.Unauthorized
            else -> LoginMessageCode.UnexpectedError
        }
    }
}