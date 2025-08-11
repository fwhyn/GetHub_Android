package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.fwhyn.app.gethub.common.di.PreferenceDi
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthTokenLocalDataSourceMainTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val preferenceModule = PreferenceDi()
    private val sharedPreferences = preferenceModule.sharedPreferences(context)

    private lateinit var authTokenLocalDataSource: AuthTokenLocalDataSourceMain

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
    fun setUp() {
        sharedPreferences.edit().clear().commit()
        authTokenLocalDataSource = AuthTokenLocalDataSourceMain(sharedPreferences)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear().commit()
    }

    // ----------------------------------------------------------------
    @Test
    fun getFirstShouldBeNullData() {
        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertNull(output)
    }

    @Test
    fun dataShouldBeNotNullAfterSet() {
        authTokenLocalDataSource.token = input

        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertNotNull(output)
    }

    @Test
    fun dataShouldBeEqualAfterSet() {
        authTokenLocalDataSource.token = input

        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertEquals(input, output)
    }

    @Test
    fun dataShouldReferToTheLatestSet() {
        authTokenLocalDataSource.token = input
        authTokenLocalDataSource.token = newInput

        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertEquals(newInput, output)
    }


    @Test
    fun dataShouldBeNullAfterClear() {
        authTokenLocalDataSource.token = input
        authTokenLocalDataSource.token = null


        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertEquals(null, output)
    }

    @Test
    fun dataShouldBeEqualAfterClearAndSet() {
        authTokenLocalDataSource.token = input
        authTokenLocalDataSource.token = null
        authTokenLocalDataSource.token = newInput

        val output: AuthTokenData? = authTokenLocalDataSource.token
        Assert.assertEquals(newInput, output)
    }
}