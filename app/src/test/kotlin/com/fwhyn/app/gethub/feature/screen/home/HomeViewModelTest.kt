package com.fwhyn.app.gethub.feature.screen.home

import app.cash.turbine.test
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCaseMain
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepositoryMain
import com.fwhyn.app.gethub.feature.func.user.di.GitHubUsersDiMain
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private val authTokenData: MutableStateFlow<AuthTokenData?> = MutableStateFlow(null)
    private val token: String
        get() = authTokenData.value?.value ?: ""

    val authTokenLocalDataSource = object : AuthTokenLocalDataSource {
        override suspend fun get(): AuthTokenData? {
            return authTokenData.value
        }

        override fun getFlow(): Flow<AuthTokenData?> {
            return authTokenData
        }

        override suspend fun set(data: AuthTokenData?) {
            authTokenData.value = data
        }

    }

    private lateinit var vm: HomeVmInterface

    @Before
    fun setUp() {
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(MockWebServerProvider.httpUrl()) { token }
        val gitHubUsersDiMain = GitHubUsersDiMain()
        val gitHubUsersRemoteDataSource = gitHubUsersDiMain.gitHubUsersRemoteDataSource(retrofit)
        val userRepository = GetGitHubUsersRepositoryMain(gitHubUsersRemoteDataSource)

        val authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)
        val logoutUseCase = LogoutUseCaseMain(authTokenRepository)

        vm = HomeViewModel(
            getGitHubUsers = userRepository,
            logoutUseCase = logoutUseCase
        )
    }

    @Test
    fun `when auth error, it will notify auth error`() = runTest {
        vm.properties.event.test {
            val data = awaitItem()
            Assert.assertEquals(HomeEvent.Notify(HomeMessageCode.Unauthorized), data)
        }
    }

    @Test
    fun `when auth error, the user list will be empty`() {

    }
}