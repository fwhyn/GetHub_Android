package com.fwhyn.app.gethub.feature.screen.login.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    param: LoginButtonParam,
) {
    Button(
        modifier = modifier,
        onClick = param.onClick,
        enabled = param.enabled,
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(stringResource(R.string.login))
    }
}

data class LoginButtonParam(
    val enabled: Boolean = true,
    val onClick: () -> Unit,
)

@Composable
fun getStateOfLoginButton(
    enabled: StateFlow<Boolean>,
    onClick: () -> Unit,
): LoginButtonParam {
    val isEnabled: Boolean by enabled.collectAsStateWithLifecycle()

    return LoginButtonParam(
        enabled = isEnabled,
        onClick = onClick
    )
}

@Preview
@Composable
fun PreviewLoginButton() {
    val isEnabled = MutableStateFlow(true)

    MyTheme {
        LoginButton(
            param = getStateOfLoginButton(
                enabled = isEnabled,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        isEnabled.value = false
                        delay(1000)
                        isEnabled.value = true
                    }
                }
            )
        )
    }
}

