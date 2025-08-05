package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    param: BackBtnParam
) {
    Button(
        modifier = modifier,
        onClick = param.onClick,
        shape = RectangleShape,
        contentPadding = PaddingValues(1.dp)
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = stringResource(R.string.back_button),
        )
    }
}

data class BackBtnParam(
    val onClick: () -> Unit
) {
    companion object {
        fun default(
            onClick: () -> Unit = {}
        ) = BackBtnParam(
            onClick = onClick
        )
    }
}

@Composable
fun getStateOfBackBtnParam(
    onClick: () -> Unit
): BackBtnParam {
    return BackBtnParam(
        onClick = onClick
    )
}

@Composable
@Preview
fun BackButtonPreview() {
    MyTheme {
        BackButton(
            param = getStateOfBackBtnParam {}
        )
    }
}