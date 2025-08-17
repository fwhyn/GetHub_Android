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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginByTokenUseCaseMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val token: MutableStateFlow<AuthTokenData?> = MutableStateFlow(null)
    val tokenValue
        get() = token.value?.value ?: ""
    val authTokenLocalDataSource = object : AuthTokenLocalDataSource {
        override suspend fun get(): AuthTokenData? {
            return token.value
        }

        override fun getFlow(): Flow<AuthTokenData?> {
            return token
        }

        override suspend fun set(data: AuthTokenData?) {
            token.value = data
        }
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
    fun setUp() = runTest {
        val authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)

        val httpUrl = MockWebServerProvider.httpUrl()
        val retrofitModule = RetrofitGitHubDiMain()
        val retrofit = retrofitModule.retrofit(
            baseUrl = httpUrl,
            onGetToken = { tokenValue }
        )
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
        authTokenLocalDataSource.set(unvalidatedLocalToken.toData())

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
        Assert.assertEquals(Status.Unauthorized, resultFailure?.status)
    }

    @Test
    fun `login success when validated local token exists`() = runTest {
        authTokenLocalDataSource.set(validatedLocalToken.toData())

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

    @Test
    fun `login success when input token is correct`() = runTest {
        loginSuccess(this)
    }

    @Test
    fun `login failed when input token is incorrect`() = runTest {
        loginFailed(this)
    }

    @Test
    fun `login failed then success when input token is incorrect then correct`() = runTest {
        loginFailed(this)
        loginSuccess(this)
    }

    private suspend fun loginSuccess(scope: CoroutineScope) {
        val correctToken = TOKEN_FAKE

        var resultSuccess: LoginByTokenResult? = null
        var resultFailure: Throwable? = null
        loginByTokenUseCase.invoke(
            scope = scope,
            onFetchParam = {
                LoginByTokenParam(token = correctToken)
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

    private suspend fun loginFailed(scope: CoroutineScope) {
        val incorrectToken = "jskdaf"

        var resultSuccess: LoginByTokenResult? = null
        var resultFailure: Exzeption? = null
        loginByTokenUseCase.invoke(
            scope = scope,
            onFetchParam = {
                LoginByTokenParam(token = incorrectToken)
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
        Assert.assertEquals(Status.Unauthorized, resultFailure?.status)
    }
}