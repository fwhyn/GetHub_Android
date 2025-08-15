package com.fwhyn.app.gethub.feature.screen.login

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.LinkTxtBtnParam
import com.fwhyn.app.gethub.common.ui.component.LinkTxtButton
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.navigateToHomeScreen
import com.fwhyn.app.gethub.feature.screen.login.component.LoginButton
import com.fwhyn.app.gethub.feature.screen.login.component.LoginButtonParam
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManager
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManagerMain
import com.fwhyn.app.gethub.feature.screen.login.component.LoginTitle
import com.fwhyn.app.gethub.feature.screen.login.component.PasswordField
import com.fwhyn.app.gethub.feature.screen.login.component.PasswordFieldParam
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfLoginButton
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfPasswordField
import com.fwhyn.app.gethub.feature.screen.login.helper.LoginConstant
import com.fwhyn.app.gethub.feature.screen.login.model.LoginEvent
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.app.gethub.feature.screen.login.model.loginPropertiesFake
import com.fwhyn.lib.baze.common.helper.extension.removeFromBackStack
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState
import com.fwhyn.lib.baze.compose.model.CommonProperties
import com.fwhyn.lib.baze.compose.model.CommonState

const val LOGIN_ROUTE = "LOGIN_ROUTE"
const val LOGIN_LOADING_TAG = "LOGIN_LOADING_TAG"
const val LOGIN_TITLE_TAG = "LOGIN_WELCOME_TAG"
const val LOGIN_PASSWORD_TAG = "LOGIN_PASSWORD_TAG"
const val LOGIN_BUTTON_TAG = "LOGIN_BUTTON_TAG"
const val LOGIN_GENERATE_TOKEN_TAG = "LOGIN_GENERATE_TOKEN_TAG"

val loginScreenModifier = Modifier
    .fillMaxSize()
    .padding(24.dp)

fun NavGraphBuilder.addLoginScreen(
    activityState: ActivityState,
) {
    composable(LOGIN_ROUTE) { backStack ->
        LoginScreen(
            modifier = loginScreenModifier,
            stringManager = LoginStringManagerMain(LocalContext.current),
            activityState = activityState,
            vm = hiltViewModel<LoginViewModel>(),
        )
    }
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE, navOptions)
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    activityState: ActivityState,
    stringManager: LoginStringManager,
    vm: LoginVmInterface,
) {
    val activity = LocalActivity.current
    BackHandler {
        activity?.moveTaskToBack(true)
    }

    // ----------------------------------------------------------------
    LaunchedEffect(Unit) {
        vm.properties.event.collect { event ->
            when (event) {
                is LoginEvent.Notify -> {
                    activityState.notification.showSnackbar(stringManager.getString(event.code))
                }

                LoginEvent.LoggedIn -> {
                    if (activityState.navigation.previousBackStackEntry != null) {
                        activityState.navigation.popBackStack()
                        activityState.navigation.popBackStack("asdf", false)
                    } else {
                        activityState.navigation.navigateToHomeScreen(
                            navOptions = navOptions { removeFromBackStack(LOGIN_ROUTE) }
                        )
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------
    val state by vm.commonProp.state.collectAsStateWithLifecycle()
    when ((state as? CommonState.Dialog<*>)?.dat) {
        is LoginState.Loading -> CircularProgressDialog(Modifier.testTag(LOGIN_LOADING_TAG))
        else -> Log.d(LOGIN_ROUTE, "Unhandled State")
    }

    // ----------------------------------------------------------------
    val passwordFieldParam = getStateOfPasswordField(
        value = vm.properties.password,
        onValueChange = vm::onPasswordChanged,
        label = stringResource(R.string.fine_grained_token),
        placeholder = stringResource(R.string.enter_the_token),
    )

    val loginButtonParam = getStateOfLoginButton(
        enabled = vm.properties.isValid,
        onClick = vm::onLogin,
    )

    val param = LoginScreenParam(
        passwordFieldParam = passwordFieldParam,
        loginButtonParam = loginButtonParam
    )

    LoginView(
        modifier = modifier,
        param = param,
    )
}

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    param: LoginScreenParam,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        val commonFieldModifier = Modifier.fillMaxWidth()

        LoginTitle(
            modifier = commonFieldModifier.testTag(LOGIN_TITLE_TAG),
        )

        MySpacer(20.dp)
        PasswordField(
            modifier = commonFieldModifier.testTag(LOGIN_PASSWORD_TAG),
            param = param.passwordFieldParam
        )

        MySpacer(20.dp)
        LoginButton(
            modifier = commonFieldModifier.testTag(LOGIN_BUTTON_TAG),
            param = param.loginButtonParam
        )

        Row(
            modifier = Modifier.testTag(LOGIN_GENERATE_TOKEN_TAG),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = stringResource(R.string.or))
            LinkTxtButton(
                param = LinkTxtBtnParam(
                    url = LoginConstant.GENERATE_FINE_GRAINED_TOKEN_URL,
                    label = stringResource(R.string.generate_token)
                )
            )
        }
    }
}

data class LoginScreenParam(
    val passwordFieldParam: PasswordFieldParam,
    val loginButtonParam: LoginButtonParam,
)

@DevicePreviews
@Composable
fun LoginScreenPreview() {
    MyTheme {
        LoginScreen(
            activityState = rememberActivityState(),
            modifier = loginScreenModifier,
            stringManager = LoginStringManagerMain(LocalContext.current),
            vm = object : LoginVmInterface() {
                override val commonProp: CommonProperties
                    get() = CommonProperties()
                override val properties: LoginProperties
                    get() = loginPropertiesFake

                init {
                    commonProp.showDialog("tag", LoginState.Loading)
                }
            }
        )
    }
}