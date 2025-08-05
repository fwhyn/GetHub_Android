package com.fwhyn.app.gethub.common.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun DataNotFoundView(
    modifier: Modifier = Modifier,
    param: DataNotFoundViewParam
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
        Text(text = stringResource(R.string.data_not_found))
    }
}

data class DataNotFoundViewParam(
    val onClicked: () -> Unit
)

@Composable
@Preview
fun DataNotFoundPreview() {
    MyTheme {
        DataNotFoundView(
            modifier = Modifier.fillMaxSize(),
            param = DataNotFoundViewParam(
                onClicked = {}
            )
        )
    }
}