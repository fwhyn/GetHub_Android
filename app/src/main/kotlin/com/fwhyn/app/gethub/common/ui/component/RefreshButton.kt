package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun RefreshButton(
    modifier: Modifier = Modifier,
    param: RefreshButtonParam
) {
    IconButton(
        onClick = param.onClicked,
    ) {
        Icon(
            modifier = modifier,
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(R.string.refresh_button)
        )
    }
}

data class RefreshButtonParam(
    val onClicked: () -> Unit
)

@Composable
fun getStateOfRefreshButtonParam(
    onClicked: () -> Unit,
): RefreshButtonParam {
    return RefreshButtonParam(
        onClicked = onClicked
    )
}

@Composable
@Preview
fun RefreshButtonPreview() {
    MyTheme {
        RefreshButton(
            param = getStateOfRefreshButtonParam(
                onClicked = {}
            )
        )
    }
}