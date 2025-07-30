package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ConnectDisconnectBtn(
    modifier: Modifier = Modifier,
    param: ConnectDisconnectBtnParam,
) {
    Button(
        modifier = modifier,
        onClick = param.onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (param.isConnected) Color(0xFF9F7070) else Color.Unspecified
        )
    ) {
        Text(
            text = if (param.isConnected) stringResource(R.string.disconnect) else stringResource(R.string.connect),
        )
    }
}

data class ConnectDisconnectBtnParam(
    val isConnected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun getStateOfConnectDisconnectBtnParam(
    isConnected: StateFlow<Boolean>,
    onClick: () -> Unit,
): ConnectDisconnectBtnParam {
    val connected: Boolean by isConnected.collectAsStateWithLifecycle()

    return ConnectDisconnectBtnParam(
        isConnected = connected,
        onClick = onClick,
    )
}

@Preview
@Composable
fun ConnectDisconnectBtnPreview() {
    val isConnected = MutableStateFlow(false)

    MyTheme {
        ConnectDisconnectBtn(
            param = getStateOfConnectDisconnectBtnParam(
                isConnected = isConnected,
                onClick = {
                    isConnected.value = !isConnected.value
                }
            )
        )
    }
}