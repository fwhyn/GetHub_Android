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

@Composable
fun TopBar(
    modifier: Modifier,
    topBarParam: TopBarParam
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MySpacer(8.dp)
        Text(
            modifier = Modifier,
            text = topBarParam.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
        )
    }
}

data class TopBarParam(
    val title: String,
) {
    companion object {
        fun default(
            title: String = "No Title",
        ) = TopBarParam(
            title = title,
        )
    }
}

@Composable
fun getStateOfTopBarParam(
    title: String,
): TopBarParam {
    return TopBarParam(
        title = title,
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
            topBarParam = getStateOfTopBarParam(title = "Home")
        )
    }
}