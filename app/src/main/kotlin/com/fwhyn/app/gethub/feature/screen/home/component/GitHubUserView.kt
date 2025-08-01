package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi

@Composable
fun GitHubUserView(
    modifier: Modifier,
    param: GitHubUserViewParam,
) {
    Row(
        modifier = modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(6.dp),
    ) {
        AsyncImage(
            model = param.user.avatarUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
        )

        Column {
            Text(
                text = param.user.login,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = param.user.htmlUrl,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

val defaultKmcUiViewHeight = 92.dp

data class GitHubUserViewParam(
    val user: GitHubUserUi,
    val onClicked: () -> Unit,
) {
    companion object {
        fun default(
            user: GitHubUserUi = GitHubUserUi.default(),
            onClicked: () -> Unit = {},
        ): GitHubUserViewParam {
            return GitHubUserViewParam(
                user = user,
                onClicked = onClicked,
            )
        }
    }
}

@Preview
@Composable
fun KmcUiViewPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GitHubUserView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(defaultKmcUiViewHeight),
                param = GitHubUserViewParam.default()
            )
        }

    }
}