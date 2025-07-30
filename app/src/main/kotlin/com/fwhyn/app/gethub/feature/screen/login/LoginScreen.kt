package com.fwhyn.app.gethub.feature.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fwhyn.lib.baze.compose.helper.ActivityState

const val LOGIN_ROUTE = "LOGIN_ROUTE"

fun NavGraphBuilder.addLoginScreen(
    activityState: ActivityState,
) {
    composable(LOGIN_ROUTE) {
        LoginRoute(
            activityState = activityState,
        )
    }
}

fun NavController.navigateToLoginScreen(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE, navOptions)
}

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    activityState: ActivityState,
    vm: LoginVmInterface = hiltViewModel<LoginViewModel>(),
) {
//    LoginScreen(
//        modifier = modifier,
//        activityState = activityState,
//        loginVmInterface = vm,
//        loginUiState = loginUiState
//    )
}

//@Composable
//fun LoginScreen(
//    modifier: Modifier = Modifier,
//    activityState: ActivityState,
//    loginVmInterface: LoginVmInterface,
//    loginUiState: LoginUiState,
//) {
//    when (val state = loginUiState.state) {
//        is LoginUiState.State.LoggedIn -> state.invokeOnce {
//            activityState.navigation.navigateToLoginScreen(navOptions { removeFromBackStack(LOGIN_ROUTE) })
//        }
//
//        is LoginUiState.State.NotLoggedIn -> {} // Do nothing
//    }
//
//    MainView(
//        modifier = modifier,
//        emailValue = email,
//        onEmailValueChange = loginVmInterface::onEmailValueChange,
//        passwordValue = pwd,
//        onPasswordValueChange = loginVmInterface::onPasswordValueChange,
//        rememberMe = rememberMe,
//        onCheckRememberMe = loginVmInterface::onCheckRememberMe,
//        isFieldNotEmpty = isNotEmpty,
//        onLogin = loginVmInterface::onLogin,
//    )
//}
//
//@Composable
//fun MainView(
//    modifier: Modifier = Modifier,
//    emailValue: String,
//    onEmailValueChange: (String) -> Unit,
//    passwordValue: String,
//    onPasswordValueChange: (String) -> Unit,
//    rememberMe: Boolean,
//    onCheckRememberMe: () -> Unit,
//    isFieldNotEmpty: Boolean,
//    onLogin: (GetAuthTokenParam) -> Unit,
//) {
//    val activity: Activity? = LocalActivity.current
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier
//            .fillMaxSize()
//            .padding(defaultPadding)
//            .verticalScroll(rememberScrollState())
//    ) {
//        val commonFieldModifier = Modifier.fillMaxWidth()
//
//        Title(
//            modifier = commonFieldModifier,
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//
//        EmailField(
//            modifier = commonFieldModifier,
//            value = emailValue,
//            onValueChange = onEmailValueChange,
//        )
//        PasswordField(
//            modifier = commonFieldModifier,
//            value = passwordValue,
//            onValueChange = onPasswordValueChange,
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Column(
//            horizontalAlignment = Alignment.End,
//            modifier = commonFieldModifier
//        ) {
//            LabeledCheckbox(
//                label = "Remember Me",
//                onCheckChanged = onCheckRememberMe,
//                isChecked = rememberMe
//            )
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                onLogin(
//                    GetAuthTokenParam.MyServer(
//                        username = emailValue,
//                        password = passwordValue,
//                        remember = rememberMe
//                    )
//                )
//            },
//            enabled = isFieldNotEmpty,
//            shape = RoundedCornerShape(5.dp),
//            modifier = commonFieldModifier
//        ) {
//            Text("Login")
//        }
////        Spacer(modifier = Modifier.height(1.dp))
////        Button(
////            onClick = {
////                // TODO add onclick register
////            },
////            shape = RoundedCornerShape(5.dp),
////            modifier = commonFieldModifier
////        ) {
////            Text("Register")
////        }
//        Spacer(modifier = Modifier.height(1.dp))
//        Button(
//            onClick = {
//                onLogin(
//                    if (activity != null) {
//                        GetAuthTokenParam.Google(activity)
//                    } else {
//                        throw Exception("Activity not found")
//                    }
//                )
//            },
//            shape = RoundedCornerShape(5.dp),
//            modifier = commonFieldModifier
//        ) {
//            Row {
//                Image(
//                    painter = painterResource(id = R.drawable.google_sign_in),
//                    contentDescription = "Google Logo",
//                    modifier = Modifier.size(24.dp)
//                )
//                Spacer(modifier = Modifier.width(10.dp))
//                Text(
//                    LocalContext.current.getString(com.google.android.gms.base.R.string.common_signin_button_text_long)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun LabeledCheckbox(
//    label: String,
//    onCheckChanged: () -> Unit,
//    isChecked: Boolean,
//) {
//
//    Row(
//        Modifier
//            .clickable(
//                onClick = onCheckChanged
//            )
//            .padding(4.dp)
//    ) {
//        Checkbox(checked = isChecked, onCheckedChange = null)
//        Spacer(Modifier.size(6.dp))
//        Text(label)
//    }
//}
//
//@Composable
//fun Title(
//    modifier: Modifier = Modifier,
//) {
//    Column(
//        modifier = modifier,
//    ) {
//        Text(
//            text = "Welcome...",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(
//            text = "Log in",
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun EmailField(
//    modifier: Modifier = Modifier,
//    value: String = "",
//    onValueChange: (String) -> Unit,
//    label: String = "Email",
//    placeholder: String = "Enter your Email",
//) {
//
//    val focusManager = LocalFocusManager.current
//    val leadingIcon = @Composable {
//        Icon(
//            Icons.Default.Person,
//            contentDescription = "",
//            tint = MaterialTheme.colorScheme.primary
//        )
//    }
//
//    TextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = leadingIcon,
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Email,
//            imeAction = ImeAction.Next
//        ),
//        keyboardActions = KeyboardActions(
//            onNext = { focusManager.moveFocus(FocusDirection.Down) }
//        ),
//        label = { Text(label) },
//        placeholder = { Text(placeholder) },
//        singleLine = true,
//    )
//}
//
//@Composable
//fun PasswordField(
//    modifier: Modifier = Modifier,
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String = "Password",
//    placeholder: String = "Enter your Password",
//) {
//
//    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
//    val focusManager = LocalFocusManager.current
//    val leadingIcon = @Composable {
//        Icon(
//            Icons.Default.Key,
//            contentDescription = "",
//            tint = MaterialTheme.colorScheme.primary
//        )
//    }
//    val trailingIcon = @Composable {
//        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//            Icon(
//                if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//                contentDescription = "",
//                tint = MaterialTheme.colorScheme.primary
//            )
//        }
//    }
//
//    TextField(
//        modifier = modifier,
//        value = value,
//        onValueChange = onValueChange,
//        leadingIcon = leadingIcon,
//        trailingIcon = trailingIcon,
//        keyboardOptions = KeyboardOptions(
//            keyboardType = KeyboardType.Password,
//            imeAction = ImeAction.Next
//        ),
//        keyboardActions = KeyboardActions(
//            onNext = { focusManager.moveFocus(FocusDirection.Down) }
//        ),
//        label = { Text(label) },
//        placeholder = { Text(placeholder) },
//        singleLine = true,
//        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
//    )
//}
//
//@DevicePreviews
//@Composable
//fun LoginScreenPreview() {
//    MyTheme {
//        LoginScreen(
//            activityState = rememberActivityState(),
//            loginVmInterface = object : LoginVmInterface() {},
//            loginUiData = LoginUiData(),
//            loginUiState = LoginUiState(),
//        )
//    }
//}