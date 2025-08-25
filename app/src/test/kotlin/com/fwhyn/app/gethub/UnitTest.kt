package com.fwhyn.app.gethub

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.TestDiMain
import dagger.Module
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@Config(
    application = HiltTestApplication::class,
    sdk = [35]
)
@HiltAndroidTest
class UnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Inject
    lateinit var string: String

    @Test
    fun `test when asserting`() {
        Assert.assertEquals("test", string)
    }
}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TestDiMain::class]
)
class TestDiTest : TestDiMain()