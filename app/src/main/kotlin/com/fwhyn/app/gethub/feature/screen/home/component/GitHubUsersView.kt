package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.component.RefreshAndTextView
import com.fwhyn.app.gethub.common.ui.component.getStateOfRefreshAndTextViewParam
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.gitHubUsersUiFake
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun GitHubUsersView(
    modifier: Modifier,
    param: GitHubUsersViewParam,
) {
    if (param.gitHubUsers.isEmpty()) {
        RefreshAndTextView(
            modifier = modifier,
            param = getStateOfRefreshAndTextViewParam(onClicked = param.onLoadNext)
        )

        return
    }

    // Custom scroll listener
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                when {
                    available.y > 0f && consumed.y == 0f -> param.onLoadPrev()
                    available.y < 0f && consumed.y == 0f -> param.onLoadNext()
                }

                return Offset.Zero
            }
        }
    }

    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.nestedScroll(nestedScrollConnection),
        state = listState
    ) {
        itemsIndexed(param.gitHubUsers) { index, user ->
            val gitHubUserViewParam = GitHubUserViewParam(
                user = user,
                onClicked = { param.onItemClicked(user) }
            )

            GitHubUserView(
                modifier = Modifier.fillMaxWidth(),
                param = gitHubUserViewParam,
            )

            if (index < param.gitHubUsers.size - 1) {
                // Add a spacer between items
                HorizontalDivider()
                MySpacer(1.dp)
            }
        }
    }
}

data class GitHubUsersViewParam(
    val gitHubUsers: List<GitHubUserUi>,
    val onItemClicked: (GitHubUserUi) -> Unit,
    val onLoadPrev: () -> Unit,
    val onLoadNext: () -> Unit,
) {
    companion object {
        fun default(
            gitHubUsers: List<GitHubUserUi> = emptyList(),
            onItemClicked: (GitHubUserUi) -> Unit = {},
            onLoadPrev: () -> Unit = {},
            onLoadNext: () -> Unit = {},
        ): GitHubUsersViewParam {
            return GitHubUsersViewParam(
                gitHubUsers = gitHubUsers,
                onItemClicked = onItemClicked,
                onLoadPrev = onLoadPrev,
                onLoadNext = onLoadNext,
            )
        }
    }
}

@Composable
fun getStateOfGitHubUsersViewParam(
    gitHubUsersFlow: StateFlow<List<GitHubUserUi>>,
    onItemClicked: (GitHubUserUi) -> Unit,
    onLoadPrev: () -> Unit,
    onLoadNext: () -> Unit,
): GitHubUsersViewParam {

    val users: List<GitHubUserUi> by gitHubUsersFlow.collectAsStateWithLifecycle()

    return GitHubUsersViewParam(
        gitHubUsers = users,
        onItemClicked = onItemClicked,
        onLoadPrev = onLoadPrev,
        onLoadNext = onLoadNext,
    )
}

@Composable
@DevicePreviews
fun GitHubUsersPreview() {

    var status by remember { mutableStateOf("None") }

    val param = getStateOfGitHubUsersViewParam(
        gitHubUsersFlow = MutableStateFlow(gitHubUsersUiFake),
        onItemClicked = { status = "Clicked on ${it.login}" },
        onLoadPrev = { status = "on load prev" },
        onLoadNext = { status = "on load next" },
    )

    MyTheme {
        Box {
            GitHubUsersView(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .fillMaxSize()
                    .padding(4.dp),
                param = param
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                text = status,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
@DevicePreviews
fun GitHubUsersEmptyPreview() {
    var status by remember { mutableStateOf("None") }

    val param = getStateOfGitHubUsersViewParam(
        gitHubUsersFlow = MutableStateFlow(emptyList()),
        onItemClicked = { status = "Clicked on ${it.login}" },
        onLoadPrev = { status = "on load prev" },
        onLoadNext = { status = "on load next" },
    )

    MyTheme {
        Box {
            GitHubUsersView(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .fillMaxSize()
                    .padding(4.dp),
                param = param
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                text = status,
                fontSize = 20.sp
            )
        }
    }
}