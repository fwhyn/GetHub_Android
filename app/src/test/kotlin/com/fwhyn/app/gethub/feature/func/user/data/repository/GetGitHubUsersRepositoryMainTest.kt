package com.fwhyn.app.gethub.feature.func.user.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.di.GitHubUsersDiMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetGitHubUsersRepositoryMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var token = ""
    private val perPage = 20

    private lateinit var getGitHubUsersRepository: GetGitHubUsersRepository

    @Before
    fun setUp() {
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(MockWebServerProvider.httpUrl()) { token }
        val gitHubUsersDiMain = GitHubUsersDiMain()
        val gitHubUsersRemoteDataSource = gitHubUsersDiMain.gitHubUsersRemoteDataSource(retrofit)
        getGitHubUsersRepository = GetGitHubUsersRepositoryMain(gitHubUsersRemoteDataSource)

    }

    @After
    fun tearDown() {

    }

    @Test
    fun `success and users exist when token is valid`() = runTest {
        token = TOKEN_FAKE

        var result: List<GitHubUserData>? = getUsers(this)
        Assert.assertEquals(true, result?.isNotEmpty())
    }

    @Test
    fun `users increased with perPage number each time called`() = runTest {
        token = TOKEN_FAKE

        var result: List<GitHubUserData>? = getUsers(this)
        Assert.assertEquals(perPage, result?.size)

        result = getUsers(this)
        Assert.assertEquals(perPage * 2, result?.size)
    }

    private suspend fun getUsers(scope: CoroutineScope): List<GitHubUserData>? {
        var result: List<GitHubUserData>? = null
        getGitHubUsersRepository.invoke(
            scope = scope,
            onFetchParam = {
                GetGitHubUsersRepoParam(perPage = perPage)
            },
            onOmitResult = {
                result = it.getOrNull()
            }
        )
        getGitHubUsersRepository.join()

        return result
    }
}