package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.authTokenDataFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.unvalidatedAuthTokenDataFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.ID
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.LOGIN
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.AuthTokenDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthUserRepositoryMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val scope = mainDispatcherRule.scope

    val authTokenLocalDataSource = AuthTokenLocalDataSourceFake()

    lateinit var authUserRepository: AuthUserRepositoryMain

    @Before
    fun setUp() = runTest {
        val httpUrl = MockWebServerProvider.httpUrl()
        val retrofitModule = RetrofitGitHubDiMain()

        val retrofit = retrofitModule.retrofit(
            baseUrl = httpUrl,
            onGetToken = { authTokenLocalDataSource.token }
        )
        val authUserRemoteDataSource = AuthTokenDiMain().authUserRemoteDataSource(retrofit)
        authUserRepository = AuthUserRepositoryMain(authUserRemoteDataSource)
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
        authTokenLocalDataSource.set(unvalidatedAuthTokenDataFake)

        val authUserData = authUserRepository.get(Unit)

        Assert.assertEquals(ID, authUserData.id)
        Assert.assertEquals(LOGIN, authUserData.login)
    }

    @Test
    fun `when token exists and validatedUser is not null, it will return success`() = scope.runTest {
        authTokenLocalDataSource.set(authTokenDataFake)

        val authUserData = authUserRepository.get(Unit)

        Assert.assertEquals(ID, authUserData.id)
        Assert.assertEquals(LOGIN, authUserData.login)
    }
}