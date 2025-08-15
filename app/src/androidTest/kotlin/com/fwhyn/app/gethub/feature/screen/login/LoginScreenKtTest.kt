package com.fwhyn.app.gethub.feature.screen.login

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManagerMain
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.app.gethub.feature.screen.login.model.loginPropertiesFake
import com.fwhyn.app.gethub.feature.screen.main.MainForTestActivity
import com.fwhyn.lib.baze.compose.helper.rememberActivityState
import com.fwhyn.lib.baze.compose.model.CommonProperties
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainForTestActivity>()

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun showLoadingWhenStateShowLoadingInvoked() {
        val vm = object : LoginVmInterface() {
            override val commonProp: CommonProperties
                get() = CommonProperties()
            override val properties: LoginProperties
                get() = loginPropertiesFake

//            init {
//                commonProp.showDialog("tag", LoginState.Loading)
//            }
        }

        composeTestRule.setContent {
            MyTheme {
                LoginScreen(
                    activityState = rememberActivityState(),
                    modifier = loginScreenModifier,
                    stringManager = LoginStringManagerMain(LocalContext.current),
                    vm = vm
                )
            }
        }

        vm.commonProp.showDialog("tag", LoginState.Loading)
        composeTestRule.onNodeWithTag(LOGIN_LOADING_TEST_TAG).assertIsDisplayed()
    }
}