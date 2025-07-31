package com.fwhyn.app.gethub.feature.screen.login

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
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.navigateToHomeScreen
import com.fwhyn.app.gethub.feature.screen.login.component.EmailField
import com.fwhyn.app.gethub.feature.screen.login.component.EmailFieldParam
import com.fwhyn.app.gethub.feature.screen.login.component.LabeledCheckbox
import com.fwhyn.app.gethub.feature.screen.login.component.LabeledCheckboxParam
import com.fwhyn.app.gethub.feature.screen.login.component.LoginButton
import com.fwhyn.app.gethub.feature.screen.login.component.LoginButtonParam
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManager
import com.fwhyn.app.gethub.feature.screen.login.component.LoginStringManagerImpl
import com.fwhyn.app.gethub.feature.screen.login.component.LoginTitle
import com.fwhyn.app.gethub.feature.screen.login.component.PasswordField
import com.fwhyn.app.gethub.feature.screen.login.component.PasswordFieldParam
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfEmailField
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfLabeledCheckbox
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfLoginButton
import com.fwhyn.app.gethub.feature.screen.login.component.getStateOfPasswordField
import com.fwhyn.app.gethub.feature.screen.login.model.LoginEvent
import com.fwhyn.app.gethub.feature.screen.login.model.LoginProperties
import com.fwhyn.app.gethub.feature.screen.login.model.LoginState
import com.fwhyn.app.gethub.feature.screen.login.model.loginPropertiesFake
import com.fwhyn.lib.baze.common.helper.extension.removeFromBackStack
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState

const val LOGIN_ROUTE = "LOGIN_ROUTE"

fun NavGraphBuilder.addLoginScreen(
    activityState: ActivityState,
) {
    composable(LOGIN_ROUTE) {
        LoginScreen(
            modifier = Modifier.fillMaxSize(),
            stringManager = LoginStringManagerImpl(LocalContext.current),
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
    // ----------------------------------------------------------------
    LaunchedEffect(Unit) {
        vm.properties.event.collect { event ->
            when (event) {
                is LoginEvent.OnNotify -> {
                    activityState.notification.showSnackbar(stringManager.getString(event.code))
                }

                LoginEvent.OnLoggedIn -> {
                    // remove Login Screen from back stack
                    val navOptions: NavOptions = navOptions { removeFromBackStack(LOGIN_ROUTE) }
                    activityState.navigation.navigateToHomeScreen(navOptions)
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

    val emailFieldParam = getStateOfEmailField(
        value = vm.properties.email,
        onValueChange = vm::onEmailChanged
    )

    val passwordFieldParam = getStateOfPasswordField(
        value = vm.properties.password,
        onValueChange = vm::onPasswordChanged
    )

    val labeledCheckboxParam = getStateOfLabeledCheckbox(
        label = stringResource(R.string.remember_me),
        onCheckChanged = vm::onRememberMeChecked,
        isChecked = vm.properties.isRememberMe
    )

    val loginButtonParam = getStateOfLoginButton(
        enabled = vm.properties.isValid,
        onClick = vm::onLogin,
    )

    val param = LoginScreenParam(
        emailFieldParam = emailFieldParam,
        passwordFieldParam = passwordFieldParam,
        labeledCheckboxParam = labeledCheckboxParam,
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

        MySpacer(20.dp)
        EmailField(
            modifier = commonFieldModifier,
            param = param.emailFieldParam
        )
        PasswordField(
            modifier = commonFieldModifier,
            param = param.passwordFieldParam
        )

        MySpacer(10.dp)
        Column(
            horizontalAlignment = Alignment.End,
            modifier = commonFieldModifier
        ) {
            LabeledCheckbox(
                param = param.labeledCheckboxParam
            )
        }

        MySpacer(20.dp)
        LoginButton(
            modifier = commonFieldModifier,
            param = param.loginButtonParam
        )
    }
}

data class LoginScreenParam(
    val emailFieldParam: EmailFieldParam,
    val passwordFieldParam: PasswordFieldParam,
    val labeledCheckboxParam: LabeledCheckboxParam,
    val loginButtonParam: LoginButtonParam,
)

@DevicePreviews
@Composable
fun LoginScreenPreview() {
    MyTheme {
        LoginScreen(
            activityState = rememberActivityState(),
            stringManager = LoginStringManagerImpl(LocalContext.current),
            vm = object : LoginVmInterface() {
                override val properties: LoginProperties
                    get() = loginPropertiesFake

            }
        )
    }
}