package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.helper.ContentDesc
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubRepoUi
import com.fwhyn.app.gethub.feature.screen.profile.model.gitHubRepoUiFake

@Composable
fun RepositoryView(
    modifier: Modifier,
    param: RepositoryViewParam,
) {
    Column(
        modifier = modifier
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = param.repo.name,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
            )

            Text(
                text = param.repo.htmlUrl,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = param.repo.description ?: stringResource(R.string.dash)
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.language) + stringResource(R.string.colon) +
                        (param.repo.language ?: stringResource(R.string.dash))
            )

            IconAndText(
                modifier = Modifier.padding(top = 4.dp),
                param = IconAndTextParam(
                    imageVector = Icons.Default.Star,
                    contentDescription = ContentDesc.STAR,
                    text = param.repo.stargazersCount.toString(),
                )
            )
        }
    }
}

data class RepositoryViewParam(
    val repo: GitHubRepoUi,
) {
    companion object {
        fun default(
            repo: GitHubRepoUi = GitHubRepoUi.default(),
        ): RepositoryViewParam {
            return RepositoryViewParam(
                repo = repo,
            )
        }
    }
}

val repositoryViewParamFake = RepositoryViewParam(
    repo = gitHubRepoUiFake,
)

@Preview
@Composable
fun RepositoryPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RepositoryView(
                modifier = Modifier.fillMaxWidth(),
                param = repositoryViewParamFake
            )
        }

    }
}