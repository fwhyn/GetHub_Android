package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.gitHubUserUiFake

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
            .padding(6.dp)
            .clickable { param.onClicked() },
    ) {
        AsyncImage(
            model = param.user.avatarUrl,
            contentDescription = stringResource(R.string.avatar),
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
        )

        MySpacer(8.dp)
        Column {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = param.user.login,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = param.user.htmlUrl,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

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

val gitHubUserViewParamFake = GitHubUserViewParam(
    user = gitHubUserUiFake,
    onClicked = {}
)

@Preview
@Composable
fun GitHubUserPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GitHubUserView(
                modifier = Modifier.fillMaxWidth(),
                param = gitHubUserViewParamFake
            )
        }

    }
}