package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.Grey02
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubUserProfileUi
import com.fwhyn.app.gethub.feature.screen.profile.model.gitHubUserProfileUiFake

@Composable
fun ProfileViewSection1(
    modifier: Modifier,
    param: ProfileViewSection1Param,
) {
    Column(
        modifier = modifier
    ) {
        Column {
            Row {
                AsyncImage(
                    model = param.userProfile.avatarUrl,
                    contentDescription = stringResource(R.string.avatar),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(color = Grey02)
                )

                MySpacer(8.dp)
                Column {
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = param.userProfile.login,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = param.userProfile.name,
                    )
                }
            }

            MySpacer(4.dp)
            HorizontalDivider(thickness = 2.dp)
        }

        MySpacer(6.dp)
        Column {
            IconAndText(
                param = IconAndTextParam(
                    imageVector = Icons.Default.Work,
                    contentDescription = stringResource(R.string.work),
                    text = param.userProfile.bio ?: stringResource(R.string.dash)
                )
            )

            IconAndText(
                param = IconAndTextParam(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.location),
                    text = param.userProfile.location ?: stringResource(R.string.dash)
                )
            )

            IconAndText(
                param = IconAndTextParam(
                    imageVector = Icons.Default.Email,
                    contentDescription = stringResource(R.string.email),
                    text = param.userProfile.email ?: stringResource(R.string.dash)
                )
            )

            IconAndText(
                param = IconAndTextParam(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.followers_and_following),
                    text = stringResource(
                        R.string.followers_following,
                        param.userProfile.followers,
                        param.userProfile.following
                    )
                )
            )

            MySpacer(2.dp)
            HorizontalDivider(thickness = 2.dp)
        }

        MySpacer(10.dp)
        Column {
            Text(
                text = stringResource(R.string.repositories) +
                        " (${param.reposViewParam.repos.size} / ${param.userProfile.publicRepos})",
                fontWeight = FontWeight.Bold
            )

            MySpacer(4.dp)
            RepositoriesView(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .height(defaultRepoItemHeight + 4.dp)
                    .fillMaxWidth(),
                param = param.reposViewParam
            )
        }

        MySpacer(2.dp)
        HorizontalDivider(thickness = 2.dp)
    }
}

data class ProfileViewSection1Param(
    val userProfile: GitHubUserProfileUi,
    val reposViewParam: RepositoriesViewParam,
) {
    companion object {
        fun default(
            user: GitHubUserProfileUi = GitHubUserProfileUi.default(),
            reposViewParam: RepositoriesViewParam = RepositoriesViewParam.default(),
        ): ProfileViewSection1Param {
            return ProfileViewSection1Param(
                userProfile = user,
                reposViewParam = reposViewParam
            )
        }
    }
}

val profileViewSection1Param = ProfileViewSection1Param(
    userProfile = gitHubUserProfileUiFake,
    reposViewParam = reposViewParamFake
)

@Preview
@Composable
fun ProfileViewSection1Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            ProfileViewSection1(
                modifier = Modifier.fillMaxWidth(),
                param = profileViewSection1Param
            )
        }
    }
}