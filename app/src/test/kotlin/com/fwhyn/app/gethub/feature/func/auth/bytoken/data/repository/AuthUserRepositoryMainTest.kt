package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.ID
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.LOGIN
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.AuthTokenDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthUserRepositoryMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val scope = mainDispatcherRule.scope

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

    lateinit var authUserRepository: AuthUserRepositoryMain

    val authToken = AuthTokenData(
        value = TOKEN_FAKE,
        validatedUser = null
    )

    val validatedAuthToken = authToken.copy(validatedUser = AuthUserData(login = LOGIN, id = ID))

    @Before
    fun setUp() = runTest {
        val httpUrl = MockWebServerProvider.httpUrl()
        val retrofitModule = RetrofitGitHubDiMain()

        val retrofit = retrofitModule.retrofit(
            baseUrl = httpUrl,
            onGetToken = { tokenValue }
        )
        val authUserRemoteDataSource = AuthTokenDiMain().authUserRemoteDataSource(retrofit)
        authUserRepository = AuthUserRepositoryMain(authUserRemoteDataSource)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `first time call when token null will be will throw Exzeption Status Unauthorized`() = runTest {
        var status: Status? = null
        try {
            authUserRepository.get(Unit)
        } catch (e: Exzeption) {
            status = e.status
        }

        Assert.assertEquals(Status.Unauthorized, status)
    }

    @Test
    fun `when token exists and validatedUser is null, it will return success`() = scope.runTest {
        authTokenLocalDataSource.set(authToken)

        val authUserData = authUserRepository.get(Unit)

        Assert.assertEquals(ID, authUserData.id)
        Assert.assertEquals(LOGIN, authUserData.login)
    }

    @Test
    fun `when token exists and validatedUser is not null, it will return success`() = scope.runTest {
        authTokenLocalDataSource.set(validatedAuthToken)

        val authUserData = authUserRepository.get(Unit)

        Assert.assertEquals(ID, authUserData.id)
        Assert.assertEquals(LOGIN, authUserData.login)
    }
}