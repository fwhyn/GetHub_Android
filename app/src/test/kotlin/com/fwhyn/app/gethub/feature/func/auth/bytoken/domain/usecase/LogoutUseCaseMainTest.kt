package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import MainDispatcherRule
import com.fwhyn.app.gethub.common.helper.extension.StatusExt
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.authTokenDataFake
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.lib.baze.common.helper.Util
import com.fwhyn.lib.baze.common.model.Exzeption
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class LogoutUseCaseMainTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var logoutUseCase: LogoutUseCase

    @Test
    fun `token will be cleared when logout success`() = runTest {
        val authTokenLocalDataSource = AuthTokenLocalDataSourceFake()
        val authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)
        logoutUseCase = LogoutUseCaseMain(authTokenRepository)

        authTokenLocalDataSource.set(authTokenDataFake)
        var actualAuthTokenData = authTokenLocalDataSource.get()
        Assert.assertEquals(authTokenDataFake, actualAuthTokenData)

        logoutUseCase.invoke(
            scope = this,
            onFetchParam = {},
            onOmitResult = {
                it.onSuccess {
                    actualAuthTokenData = authTokenLocalDataSource.get()
                }.onFailure {
                    Util.throwMustNotFailed()
                }
            }
        )
        logoutUseCase.join()
        Assert.assertEquals(null, actualAuthTokenData)
    }

    @Test
    fun `resulting logout error when logout failed`() = runTest {
        val authTokenRepository = mockk<AuthTokenRepository>(relaxed = true)
        logoutUseCase = LogoutUseCaseMain(authTokenRepository)

        coEvery { authTokenRepository.set(Unit, null) } answers { throw Exception() }

        var error: Exzeption? = null
        logoutUseCase.invoke(
            scope = this,
            onFetchParam = {},
            onOmitResult = {
                it.onSuccess {
                    Util.throwMustNotSuccess()
                }.onFailure { err ->
                    error = err as? Exzeption
                }
            }
        )
        logoutUseCase.join()

        Assert.assertEquals(StatusExt.LogoutError, error?.status)
    }
}