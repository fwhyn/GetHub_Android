package com.fwhyn.app.gethub.feature.screen.login

import MainDispatcherRule
import app.cash.turbine.test
import assertNoProducedValue
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCase
import com.fwhyn.app.gethub.feature.screen.login.model.LoginEvent
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import com.fwhyn.lib.baze.compose.model.CommonState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val scope = mainDispatcherRule.scope

    private lateinit var loginViewModel: LoginVmInterface

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    val loginUseCaseErrorUnauthorized = object : LoginByTokenUseCase() {
        override suspend fun onRunning(
            param: LoginByTokenParam,
            result: suspend (LoginByTokenResult) -> Unit
        ) {
            throw Exzeption(Status.Unauthorized)
        }
    }.apply { setWorkerContext(scope.coroutineContext) }

    @Test
    fun `first time login error will not notify UI error`() = runTest {
        loginViewModel = LoginViewModel(loginUseCaseErrorUnauthorized)

        val event = loginViewModel.properties.event
        event.test {
            assertNoProducedValue()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `first time login error will set isFirstLogin to false`() = runTest {
        loginViewModel = LoginViewModel(loginUseCaseErrorUnauthorized)

        advanceTimeBy(1000)
        val isFirstLogin = loginViewModel.properties.isFirstLogin
        Assert.assertEquals(false, isFirstLogin)
    }

    val loginUseCaseSuccess = object : LoginByTokenUseCase() {
        override suspend fun onRunning(
            param: LoginByTokenParam,
            result: suspend (LoginByTokenResult) -> Unit
        ) {
            result(LoginByTokenResult(AuthUserDomain("", 1)))
        }
    }.apply { setWorkerContext(scope.coroutineContext) }

    @Test
    fun `when login success will notify LoggedIn event`() = runTest {
        loginViewModel = LoginViewModel(loginUseCaseSuccess)

        val event = loginViewModel.properties.event
        event.test {
            val data = awaitItem()
            Assert.assertEquals(LoginEvent.LoggedIn, data)
        }
    }

    @Test
    fun `when logging in will notify State Loading and Idle event`() = runTest {
        loginViewModel = LoginViewModel(loginUseCaseSuccess)

        val event = loginViewModel.commonProp.state
        event.test {
            val states: ArrayList<CommonState> = arrayListOf()

            states.add(awaitItem())
            states.add(awaitItem())
            states.add(awaitItem())

            Assert.assertEquals(CommonState.Idle, states[0])
            Assert.assertEquals(LoginState.Loading, (states[1] as? CommonState.Dialog<*>)?.dat)
            Assert.assertEquals(CommonState.Idle, states[2])
        }
    }
}