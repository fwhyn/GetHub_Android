package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginByTokenUseCaseMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val authTokenRepository: AuthTokenRepository = mockk(relaxed = true)
    private val authUserRepository: AuthUserRepository = mockk(relaxed = true)

    private lateinit var loginByTokenUseCase: LoginByTokenUseCaseMain

    private val localToken = AuthTokenDomain(
        value = "local_token_value",
        validatedUser = AuthUserDomain(
            login = "local_user",
            id = 12345,
        )
    )

    // ----------------------------------------------------------------
    @Before
    fun setUp() {
        loginByTokenUseCase = LoginByTokenUseCaseMain(
            authTokenRepository = authTokenRepository,
            authUserRepository = authUserRepository
        )
    }

    @After
    fun tearDown() {

    }

    // ----------------------------------------------------------------
    @Test
    fun `first login when no local token yet`() = runTest {
        coEvery { authTokenRepository.get(Unit) } returns null

        var resultSuccess: LoginByTokenResult? = null
        var resultFailure: Exzeption? = null
        loginByTokenUseCase.invoke(
            scope = this,
            onFetchParam = {
                LoginByTokenParam(token = null)
            },
            onOmitResult = {
                it.onSuccess { output ->
                    resultSuccess = output
                }.onFailure { error ->
                    resultFailure = error as? Exzeption
                }
            }
        )
        loginByTokenUseCase.join()

        Assert.assertEquals(null, resultSuccess)
        Assert.assertEquals(Status.NotFound, resultFailure?.status)
    }

    @Test
    fun `login success when local token exists`() = runTest {
        coEvery { authTokenRepository.get(Unit) } returns localToken.toData()

        var resultSuccess: LoginByTokenResult? = null
        var resultFailure: Throwable? = null
        loginByTokenUseCase.invoke(
            scope = this,
            onFetchParam = {
                LoginByTokenParam(token = null)
            },
            onOmitResult = {
                it.onSuccess { output ->
                    resultSuccess = output
                }.onFailure { error ->
                    resultFailure = error
                }
            }
        )
        loginByTokenUseCase.join()

        Assert.assertEquals(localToken.validatedUser, resultSuccess?.user)
        Assert.assertEquals(null, resultFailure)
    }
}