package com.fwhyn.app.gethub.feature.screen.home

import MainDispatcherRule
import app.cash.turbine.test
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.authTokenDataFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCaseMain
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepositoryMain
import com.fwhyn.app.gethub.feature.func.user.di.GitHubUsersDiMain
import com.fwhyn.app.gethub.feature.screen.home.component.HomeMessageCode
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    val authTokenLocalDataSource = AuthTokenLocalDataSourceFake()

    private lateinit var vm: HomeVmInterface

    @Before
    fun setUp() = runTest {
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(MockWebServerProvider.httpUrl()) {
            authTokenLocalDataSource.token
        }
        val gitHubUsersDiMain = GitHubUsersDiMain()
        val gitHubUsersRemoteDataSource = gitHubUsersDiMain.gitHubUsersRemoteDataSource(retrofit)
        val userRepository = GetGitHubUsersRepositoryMain(gitHubUsersRemoteDataSource)

        val authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)
        val logoutUseCase = LogoutUseCaseMain(authTokenRepository)

        authTokenLocalDataSource.set(null)
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

            val data1 = awaitItem()
            Assert.assertEquals(HomeEvent.LoggedOut, data1)
        }
    }

    @Test
    fun `when auth error, the user list will be empty`() = runTest {
        vm.properties.gitHubUsers.test {
            val data = awaitItem()
            Assert.assertEquals(emptyList<GitHubUserUi>(), data)
        }
    }

    @Test
    fun `when auth success, the user list will not empty`() = runTest {
        vm.properties.gitHubUsers.test {
            // Initial value
            val data = awaitItem()
            Assert.assertEquals(emptyList<GitHubUserUi>(), data)

            // Loaded value
            authTokenLocalDataSource.set(authTokenDataFake)
            val data1 = awaitItem()
            Assert.assertEquals(true, data1.isNotEmpty())
        }
    }
}