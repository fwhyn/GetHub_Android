package com.fwhyn.app.gethub.feature.screen.login

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManagerMain
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.app.gethub.feature.screen.main.MainForTestActivity
import com.fwhyn.lib.baze.compose.helper.rememberActivityState
import com.fwhyn.lib.baze.compose.model.CommonProperties
import com.fwhyn.lib.baze.compose.model.CommonState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName

@HiltAndroidTest
class LoginScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainForTestActivity>()

    @get:Rule(order = 2)
    val testName = TestName()

    private val pass = MutableStateFlow("")
    private val isValid = MutableStateFlow(false)
    private val loginProperties = LoginProperties.default(
        password = pass,
        isValid = isValid
    )
    private val commonPropSpy = spyk(CommonProperties())

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val stringManager = LoginStringManagerMain(context)


    val vm = object : LoginVmInterface() {
        override val commonProp: CommonProperties
            get() = commonPropSpy
        override val properties: LoginProperties
            get() = loginProperties
    }

    // ----------------------------------------------------------------
    @Before
    fun setUp() {
        when (testName.methodName) {
            "showLoadingWhenInvoked" -> return
        }

        contentSetup()
    }

    // ----------------------------------------------------------------
    @Test
    fun showLoadingWhenInvoked() {
        every { commonPropSpy.state } answers { MutableStateFlow(CommonState.Dialog("Loading", LoginState.Loading)) }
        contentSetup()

        composeTestRule.onNodeWithTag(LOGIN_LOADING_TAG)
            .assertIsDisplayed()
    }

    @Test
    fun loginTitleIsShown() {
        composeTestRule.onNodeWithText(context.getString(R.string.welcome))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(context.getString(R.string.log_in))
            .assertIsDisplayed()
    }

    @Test
    fun passwordFiledIsShown() {
        composeTestRule.onNodeWithText(context.getString(R.string.fine_grained_token))
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.password_icon))
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.password_hide_unhide))
            .assertIsDisplayed()
    }

    @Test
    fun loginButtonIsShown() {
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG)
            .assertIsDisplayed()
            .assertTextEquals(context.getString(R.string.login))
    }

    @Test
    fun generateTokenButtonIsShown() {
        composeTestRule.onNodeWithText(context.getString(R.string.or))
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(context.getString(R.string.generate_token))
            .assertIsDisplayed()
    }

    @Test
    fun generateTokenButtonIsClickable() {
        composeTestRule.onNodeWithText(context.getString(R.string.generate_token))
            .assertHasClickAction()
            .assertIsEnabled()
    }

    @Test
    fun loginButtonIsNotClickableWhenIsNotValid() {
        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG)
            .assertHasClickAction()
            .assertIsNotEnabled()
    }

    @Test
    fun loginButtonIsClickableWhenIsValid() {
        isValid.value = true

        composeTestRule.onNodeWithTag(LOGIN_BUTTON_TAG)
            .assertHasClickAction()
            .assertIsEnabled()
    }

    // ----------------------------------------------------------------
    private fun contentSetup() {
        composeTestRule.setContent {
            MyTheme {
                LoginScreen(
                    activityState = rememberActivityState(),
                    modifier = loginScreenModifier,
                    stringManager = stringManager,
                    vm = vm
                )
            }
        }
    }
}