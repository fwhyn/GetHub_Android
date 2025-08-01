package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.gitHubUserUiFake
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DataStreamView(
    modifier: Modifier,
    param: DataStreamViewParam,
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(param.gitHubUsers) { index, user ->
                val gitHubUserViewParam = GitHubUserViewParam(
                    user = user,
                    onClicked = { param.onItemClicked(user) }
                )

                GitHubUserView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(defaultKmcUiViewHeight),
                    param = gitHubUserViewParam,
                )
            }
        }
    }
}

data class DataStreamViewParam(
    val gitHubUsers: List<GitHubUserUi>,
    val onItemClicked: (GitHubUserUi) -> Unit,
) {
    companion object {
        fun default(
            gitHubUsers: List<GitHubUserUi> = emptyList(),
            onItemClicked: (GitHubUserUi) -> Unit = {},
        ): DataStreamViewParam {
            return DataStreamViewParam(
                gitHubUsers = gitHubUsers,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun getStateOfDataStreamViewParam(
    gitHubUsersFlow: StateFlow<List<GitHubUserUi>>,
    onItemClicked: (GitHubUserUi) -> Unit,
): DataStreamViewParam {

    val users: List<GitHubUserUi> by gitHubUsersFlow.collectAsStateWithLifecycle()

    return DataStreamViewParam(
        gitHubUsers = users,
        onItemClicked = onItemClicked
    )
}

@Composable
@Preview
fun DataStreamViewPreview() {
    val param = getStateOfDataStreamViewParam(
        gitHubUsersFlow = MutableStateFlow(gitHubUserUiFake),
        onItemClicked = {}
    )

    MyTheme {
        DataStreamView(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .fillMaxSize()
                .padding(4.dp),
            param = param
        )
    }
}