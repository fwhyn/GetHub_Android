package com.fwhyn.app.gethub.feature.func.user.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.LIMITED_TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.TOKEN_FAKE
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.user.data.model.GetGitHubUsersRepoParam
import com.fwhyn.app.gethub.feature.func.user.data.model.GitHubUserData
import com.fwhyn.app.gethub.feature.func.user.di.GitHubUsersDiMain
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
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

    // ----------------------------------------------------------------
    @Before
    fun setUp() {
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(MockWebServerProvider.httpUrl()) { token }
        val gitHubUsersDiMain = GitHubUsersDiMain()
        val gitHubUsersRemoteDataSource = gitHubUsersDiMain.gitHubUsersRemoteDataSource(retrofit)
        getGitHubUsersRepository = GetGitHubUsersRepositoryMain(gitHubUsersRemoteDataSource)
    }

    // ----------------------------------------------------------------
    @Test
    fun `success and users exist when token is valid`() = runTest {
        token = TOKEN_FAKE

        var result: List<GitHubUserData>? = getUsers(this)
        Assert.assertEquals(true, result?.isNotEmpty())
    }

    @Test
    fun `users increased with perPage number each time called`() = runTest {
        token = TOKEN_FAKE

        var users: List<GitHubUserData>? = getUsers(this)
        Assert.assertEquals(perPage, users?.size)

        users = getUsers(this)
        Assert.assertEquals(perPage * 2, users?.size)
    }

    @Test
    fun `no user returned when load with invalid token`() = runTest {
        token = ""

        val users = getUsers(this)
        Assert.assertEquals(null, users?.size)
    }

    @Test
    fun `unauthorized error returned when load with invalid token`() = runTest {
        token = ""

        unAuthorizedError(this)
    }

    @Test
    fun `unauthorized error returned when load with limited token`() = runTest {
        token = LIMITED_TOKEN_FAKE
        unAuthorizedError(this)
    }

    private suspend fun unAuthorizedError(scope: CoroutineScope) {
        val result = getResult(scope)
        var error: Exzeption? = null
        result.onFailure { error = it as? Exzeption }
        Assert.assertEquals(Status.Unauthorized, error?.status)
    }

    private suspend fun getUsers(scope: CoroutineScope): List<GitHubUserData>? {
        var result = getResult(scope)
        return result.getOrNull()
    }

    private suspend fun getResult(scope: CoroutineScope): Result<List<GitHubUserData>> {
        var result: Result<List<GitHubUserData>> = Result.failure(Exception())
        getGitHubUsersRepository.invoke(
            scope = scope,
            onFetchParam = {
                GetGitHubUsersRepoParam(perPage = perPage)
            },
            onOmitResult = {
                result = it
            }
        )
        getGitHubUsersRepository.join()

        return result
    }
}