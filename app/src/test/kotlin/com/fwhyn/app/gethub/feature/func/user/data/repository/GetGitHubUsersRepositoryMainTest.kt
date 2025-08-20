package com.fwhyn.app.gethub.feature.func.user.data.repository

import MainDispatcherRule
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.RetrofitGitHubDiMain
import com.fwhyn.app.gethub.feature.func.user.di.GitHubUsersDiMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

class GetGitHubUsersRepositoryMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var token = ""

    private lateinit var getGitHubUsersRepository: GetGitHubUsersRepository

    @Before
    fun setUp() {
        val retrofitGitHubDiMain = RetrofitGitHubDiMain()
        val retrofit = retrofitGitHubDiMain.retrofit(MockWebServerProvider.httpUrl()) { "" }
        val gitHubUsersDiMain = GitHubUsersDiMain()
        val gitHubUsersRemoteDataSource = gitHubUsersDiMain.gitHubUsersRemoteDataSource(retrofit)
        getGitHubUsersRepository = GetGitHubUsersRepositoryMain(gitHubUsersRemoteDataSource)

    }

    @After
    fun tearDown() {

    }
}