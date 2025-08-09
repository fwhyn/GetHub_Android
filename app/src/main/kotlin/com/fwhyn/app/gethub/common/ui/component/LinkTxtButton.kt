package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.config.Pink80
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LinkTxtButton(
    modifier: Modifier = Modifier,
    param: LinkTxtBtnParam
) {
    // TODO fix bug that does not work in some devices
    val uriHandler = LocalUriHandler.current
    TextButton(
        modifier = modifier,
        onClick = {
            uriHandler.openUri(param.url)
        }
    ) {
        Text(
            text = param.label,
            color = Pink80
        )
    }
}

data class LinkTxtBtnParam(
    val url: String,
    val label: String,
)

@Composable
fun getStateOfLinkTxtBtnParam(
    urlFlow: StateFlow<String>,
    labelFlow: StateFlow<String>
): LinkTxtBtnParam {
    val url by urlFlow.collectAsStateWithLifecycle()
    val label by labelFlow.collectAsStateWithLifecycle()

    return LinkTxtBtnParam(
        url = url,
        label = label
    )
}

@Composable
@Preview
fun LinkTxtButtonPreview() {
    LinkTxtButton(
        param = LinkTxtBtnParam(
            url = "https://www.example.com",
            label = "Visit Example"
        )
    )
}