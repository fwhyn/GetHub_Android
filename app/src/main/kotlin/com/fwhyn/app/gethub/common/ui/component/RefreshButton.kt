package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fwhyn.app.gethub.R

@Composable
fun RefreshButton(
    modifier: Modifier,
    param: ReloadButtonParam
) {
    IconButton(
        modifier = modifier,
        onClick = param.onClicked,
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(R.string.refresh_button)
        )
    }
}

data class ReloadButtonParam(
    val onClicked: () -> Unit
)