package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RefreshAndTextView(
    modifier: Modifier = Modifier,
    param: RefreshAndTextViewParam,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val refreshButtonParam = RefreshButtonParam(onClicked = param.onClicked)
        RefreshButton(
            modifier = Modifier.size(100.dp),
            param = refreshButtonParam
        )

        MySpacer(8.dp)
        Text(text = param.errorMessage)
    }
}

data class RefreshAndTextViewParam(
    val errorMessage: String,
    val onClicked: () -> Unit,
)

@Composable
fun getStateOfRefreshAndTextViewParam(
    errorFlow: StateFlow<String> = MutableStateFlow(stringResource(R.string.empty_data)),
    onClicked: () -> Unit,
): RefreshAndTextViewParam {
    val error: String by errorFlow.collectAsStateWithLifecycle()

    return RefreshAndTextViewParam(
        errorMessage = error,
        onClicked = onClicked,
    )
}

@Composable
@Preview
fun RefreshAndTextPreview() {
    MyTheme {
        RefreshAndTextView(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            param = getStateOfRefreshAndTextViewParam(
                onClicked = {},
            )
        )
    }
}