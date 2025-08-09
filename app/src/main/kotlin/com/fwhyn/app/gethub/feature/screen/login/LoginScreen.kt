package com.fwhyn.app.gethub.feature.screen.login

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fwhyn.app.gethub.R
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
import com.fwhyn.app.gethub.feature.screen.login.model.LoginEvent
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.app.gethub.feature.screen.login.model.loginPropertiesFake
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState

private const val CALLER_ROUTE = "CALLER_ROUTE"
const val LOGIN_ROUTE = "LOGIN_ROUTE"

fun NavGraphBuilder.addLoginScreen(
    activityState: ActivityState,
) {
    composable(
        route = LOGIN_ROUTE,
        arguments = listOf(navArgument(CALLER_ROUTE) { type = NavType.StringType })
    ) { backStack ->
//        val vm = hiltViewModel<LoginViewModel>()
//        val callerRoute = backStack.arguments?.getString(CALLER_ROUTE) ?: ""
//        vm.onUpdateCallerRoute(callerRoute)

        LoginScreen(
            modifier = Modifier.fillMaxSize(),
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
                    } else {
                        activityState.navigation.navigateToHomeScreen()
                    }
                }
            }
        }
    }

    // ----------------------------------------------------------------
    val state by vm.properties.state.collectAsStateWithLifecycle()
    when (state) {
        LoginState.Idle -> {} // do nothing
        LoginState.Loading -> CircularProgressDialog()
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val commonFieldModifier = Modifier.fillMaxWidth()

        LoginTitle(
            modifier = commonFieldModifier,
        )

        // TODO add link to GitHub token creation page
        // https://github.com/settings/personal-access-tokens/new
        MySpacer(20.dp)
        PasswordField(
            modifier = commonFieldModifier,
            param = param.passwordFieldParam
        )

        MySpacer(20.dp)
        LoginButton(
            modifier = commonFieldModifier,
            param = param.loginButtonParam
        )
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
            stringManager = LoginStringManagerMain(LocalContext.current),
            vm = object : LoginVmInterface() {
                override val properties: LoginProperties
                    get() = loginPropertiesFake

            }
        )
    }
}