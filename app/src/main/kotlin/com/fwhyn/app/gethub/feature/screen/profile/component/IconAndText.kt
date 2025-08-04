package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun IconAndText(
    modifier: Modifier = Modifier,
    param: IconAndTextParam,
) {
    Column(
        modifier = modifier,
    ) {
        MySpacer(4.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = param.imageVector,
                contentDescription = param.contentDescription,
                tint = MaterialTheme.colorScheme.primary
            )

            MySpacer(4.dp)
            Text(
                text = param.text
            )
        }
    }
}

data class IconAndTextParam(
    val imageVector: ImageVector,
    val contentDescription: String,
    val text: String,
)

@Preview
@Composable
fun IconAndTextPreview() {
    MyTheme {
        IconAndText(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            param = IconAndTextParam(
                imageVector = androidx.compose.material.icons.Icons.Default.Work,
                contentDescription = "Work Icon",
                text = "Work"
            )
        )
    }
}