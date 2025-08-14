package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.ID
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.LOGIN
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.AuthTokenDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthUserRepositoryMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val authTokenLocalDataSource = object : AuthTokenLocalDataSource {
        override var token: AuthTokenData? = null
    }

    lateinit var authUserRepository: AuthUserRepositoryMain

    val authToken = AuthTokenData(
        value = TOKEN_FAKE,
        validatedUser = null
    )

    @Before
    fun setUp() {
        val httpUrl = MockWebServerProvider.get().url("/")
        val retrofitModule = RetrofitGitHubDiMain()
        val builder = retrofitModule.retrofitBuilder(httpUrl)

        val retrofit = retrofitModule.retrofit(
            builder = builder,
            authTokenLocalDataSource = authTokenLocalDataSource
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
    fun `when token exists and validatedUser is null, it will return success`() = runTest {
        authTokenLocalDataSource.token = authToken

        val authUserData = authUserRepository.get(Unit)

        Assert.assertEquals(ID, authUserData.id)
        Assert.assertEquals(LOGIN, authUserData.login)
    }
}