package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
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

    lateinit var authUserRepository: AuthUserRepositoryMain

    @Before
    fun setUp() {
        val httpUrl = MockWebServerProvider.get().url("/")
        val retrofitModule = RetrofitGitHubDiMain()
        val builder = retrofitModule.retrofitBuilder(httpUrl)
        val authTokenLocalDataSource = object : AuthTokenLocalDataSource {
            override var token: AuthTokenData? = null
        }
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
}