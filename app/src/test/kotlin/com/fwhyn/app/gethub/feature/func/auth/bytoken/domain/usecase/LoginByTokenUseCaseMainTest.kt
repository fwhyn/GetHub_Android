package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.ID
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.LOGIN
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.AuthTokenDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginByTokenUseCaseMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val authTokenLocalDataSource = object : AuthTokenLocalDataSource {
        override var token: AuthTokenData? = null
    }

    private lateinit var loginByTokenUseCase: LoginByTokenUseCaseMain

    private val unvalidatedLocalToken = AuthTokenDomain(
        value = TOKEN_FAKE,
        validatedUser = null
    )

    private val validatedLocalToken = AuthTokenDomain(
        value = TOKEN_FAKE,
        validatedUser = AuthUserDomain(
            login = LOGIN,
            id = ID,
        )
    )

    // ----------------------------------------------------------------
    @Before
    fun setUp() {
        val authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)

        val httpUrl = MockWebServerProvider.httpUrl()
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(httpUrl, authTokenLocalDataSource)
        val authTokenDiMain = AuthTokenDiMain()
        val authUserRemoteDataSource = authTokenDiMain.authUserRemoteDataSource(retrofit)
        val authUserRepository = AuthUserRepositoryMain(authUserRemoteDataSource)

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
    fun `login failed when unvalidated local token exists`() = runTest {
        authTokenLocalDataSource.token = unvalidatedLocalToken.toData()

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
    fun `login success when validated local token exists`() = runTest {
        authTokenLocalDataSource.token = validatedLocalToken.toData()

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

        Assert.assertEquals(validatedLocalToken.validatedUser, resultSuccess?.user)
        Assert.assertEquals(null, resultFailure)
    }
}