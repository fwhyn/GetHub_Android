package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.component.LogoutBtnParam
import com.fwhyn.app.gethub.feature.screen.home.component.LogoutButton

@Composable
fun TopBar(
    modifier: Modifier,
    param: TopBarParam,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MySpacer(8.dp)
        Text(
            modifier = Modifier.weight(1f),
            text = param.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
        )

        param.trailingContent?.invoke()
    }
}

data class TopBarParam(
    val title: String,
    val trailingContent: (@Composable () -> Unit)?,
) {
    companion object {
        fun default(
            title: String = "No Title",
            trailingContent: (@Composable () -> Unit)? = null,
        ) = TopBarParam(
            title = title,
            trailingContent = trailingContent
        )
    }
}

@Composable
fun getStateOfTopBarParam(
    title: String,
    trailingContent: (@Composable () -> Unit)? = null,
): TopBarParam {
    return TopBarParam(
        title = title,
        trailingContent = trailingContent
    )
}

@Composable
@Preview
fun TopBarPreview() {
    MyTheme {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            param = getStateOfTopBarParam(
                title = "Home",
            )
        )
    }
}

@Composable
@Preview
fun TopBarWithButtonPreview() {
    MyTheme {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            param = getStateOfTopBarParam(
                title = "Home",
            ) {
                LogoutButton(param = LogoutBtnParam.default())
            }
        )
    }
}