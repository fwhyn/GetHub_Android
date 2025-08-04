package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.common.helper.ContentDesc
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun LogoutButton(
    modifier: Modifier = Modifier,
    param: LogoutBtnParam
) {
    Button(
        modifier = modifier.size(40.dp),
        onClick = param.onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = ContentDesc.LOGOUT_BUTTON,
        )
    }
}

data class LogoutBtnParam(
    val onClick: () -> Unit
) {
    companion object {
        fun default(
            onClick: () -> Unit = {}
        ) = LogoutBtnParam(
            onClick = onClick
        )
    }
}


@Composable
fun getStateOfLogoutBtnParam(
    onClick: () -> Unit
): LogoutBtnParam {
    return LogoutBtnParam(
        onClick = onClick
    )
}

@Composable
@Preview
private fun LogoutButtonPreview() {
    MyTheme {
        LogoutButton(
            param = getStateOfLogoutBtnParam {}
        )
    }
}