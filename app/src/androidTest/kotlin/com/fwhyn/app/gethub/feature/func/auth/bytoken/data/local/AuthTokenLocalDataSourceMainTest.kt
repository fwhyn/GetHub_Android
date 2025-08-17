package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AuthTokenLocalDataSourceMainTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

//    private val context = ApplicationProvider.getApplicationContext<Context>()
//    private val preferenceModule = DataStoreDiMain()
//    private val preferences = preferenceModule.encryptedUserPrefsDataStore(context)

    @Inject
    lateinit var authTokenLocalDataSource: AuthTokenLocalDataSource

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
        hiltRule.inject()

        authTokenLocalDataSource.set(null)
    }

    @After
    fun tearDown() = runTest {
        authTokenLocalDataSource.set(null)
    }

    // ----------------------------------------------------------------
    @Test
    fun getFirstDataShouldBeNullData() = runTest {
        val output: AuthTokenData? = authTokenLocalDataSource.get()
        Assert.assertNull(output)
    }

    @Test
    fun dataShouldBeEqualAfterSet() = runTest {
        authTokenLocalDataSource.set(input)

        val output: AuthTokenData? = authTokenLocalDataSource.get()
        Assert.assertEquals(input, output)
    }

    @Test
    fun dataShouldReferToTheLatestSet() = runTest {
        authTokenLocalDataSource.set(input)
        authTokenLocalDataSource.set(newInput)

        val output: AuthTokenData? = authTokenLocalDataSource.get()
        Assert.assertEquals(newInput, output)
    }


    @Test
    fun dataShouldBeNullAfterClear() = runTest {
        authTokenLocalDataSource.set(input)
        authTokenLocalDataSource.set(null)


        val output: AuthTokenData? = authTokenLocalDataSource.get()
        Assert.assertEquals(null, output)
    }

    @Test
    fun dataShouldBeEqualAfterClearAndSet() = runTest {
        authTokenLocalDataSource.set(input)
        authTokenLocalDataSource.set(null)
        authTokenLocalDataSource.set(newInput)

        val output: AuthTokenData? = authTokenLocalDataSource.get()
        Assert.assertEquals(newInput, output)
    }
}