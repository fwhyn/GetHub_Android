package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.app.gethub.feature.func.datastore.di.DataStoreDi
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthTokenRepositoryMainTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val preferenceModule = DataStoreDi()
    private val preferences = preferenceModule.encryptedUserPrefsDataStore(context)
    private val authTokenLocalDataSource: AuthTokenLocalDataSourceMain = AuthTokenLocalDataSourceMain(preferences)
    private lateinit var authTokenRepository: AuthTokenRepositoryMain

    private val input = AuthTokenData(
        value = "test_token",
        validatedUser = AuthUserData(
            login = "fwhyn",
            id = 123456,
        )
    )
    private val newInput = AuthTokenData(
        value = "new_test_token",
        validatedUser = AuthUserData(
            login = "new_fwhyn",
            id = 654321,
        )
    )

    // ----------------------------------------------------------------
    @Before
    fun setUp() = runTest {
        authTokenLocalDataSource.set(null)
        authTokenRepository = AuthTokenRepositoryMain(authTokenLocalDataSource)
    }

    @After
    fun tearDown() = runTest {
        authTokenLocalDataSource.set(null)
    }

    // ----------------------------------------------------------------
    @Test
    fun getFirstTimeShouldThrowNotFoundException() = runTest {
        var status: Status? = null
        try {
            authTokenRepository.get(Unit)
        } catch (e: Exzeption) {
            status = e.status
        }
        Assert.assertEquals(Status.NotFound, status)
    }

    @Test
    fun dataShouldExistsAfterSet() = runTest {
        authTokenRepository.set(Unit, input)

        val output: AuthTokenData = authTokenRepository.get(Unit)
        Assert.assertEquals(input, output)
    }


    @Test
    fun dataShouldReferToTheLatestSet() = runTest {
        authTokenRepository.set(Unit, input)
        authTokenRepository.set(Unit, newInput)

        val output: AuthTokenData? = authTokenRepository.get(Unit)
        Assert.assertEquals(newInput, output)
    }


    @Test
    fun dataShouldBeNotFoundAfterClear() = runTest {
        authTokenRepository.set(Unit, input)
        authTokenRepository.set(Unit, null)

        var status: Status? = null
        try {
            authTokenRepository.get(Unit)
        } catch (e: Exzeption) {
            status = e.status
        }
        Assert.assertEquals(Status.NotFound, status)
    }

    @Test
    fun dataShouldBeEqualAfterClearAndSet() = runTest {
        authTokenRepository.set(Unit, input)
        authTokenRepository.set(Unit, null)
        authTokenRepository.set(Unit, newInput)

        val output: AuthTokenData? = authTokenRepository.get(Unit)
        Assert.assertEquals(newInput, output)
    }
}