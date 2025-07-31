package com.fwhyn.app.gethub.feature.screen.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun LoginTitle(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.welcome),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        MySpacer(10.dp)
        Text(
            text = stringResource(R.string.log_in),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PreviewLoginTitle() {
    MyTheme {
        LoginTitle()
    }
}