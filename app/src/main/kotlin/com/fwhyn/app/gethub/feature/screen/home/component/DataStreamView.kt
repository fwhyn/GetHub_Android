package com.fwhyn.app.gethub.feature.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.GitHubUserUi
import com.fwhyn.app.gethub.feature.screen.home.model.gitHubUsersUiFake
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
        val listState = rememberLazyListState()
        var previousScrollOffset by remember { mutableIntStateOf(0) }
        var items by remember { mutableStateOf(param.gitHubUsers) }
        items = param.gitHubUsers

        LaunchedEffect(listState) {
            listState.interactionSource.interactions.collect {
                // Check if at the top
                if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0) {
                    param.onLoadPrev()
                }

                // Check if at the bottom
                val lastIndex = items.lastIndex
                if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lastIndex) {
                    param.onLoadNext()
                }
            }

//            combine(
//                listState.interactionSource.interactions,
//                snapshotFlow { listState.firstVisibleItemScrollOffset }
//            ) { interaction: Interaction, currentOffset: Int ->
//                val noScroll = currentOffset == previousScrollOffset
//
//                previousScrollOffset = currentOffset
//
//                // Check if at the top
//                if (listState.firstVisibleItemIndex == 0 && noScroll) {
//                    param.onLoadPrev()
//                }
//
//                // Check if at the bottom
//                val lastIndex = param.gitHubUsers.lastIndex
//                if (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lastIndex && noScroll) {
//                    param.onLoadNext()
//                }
//            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
}

data class DataStreamViewParam(
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
        ): DataStreamViewParam {
            return DataStreamViewParam(
                gitHubUsers = gitHubUsers,
                onItemClicked = onItemClicked,
                onLoadPrev = onLoadPrev,
                onLoadNext = onLoadNext,
            )
        }
    }
}

@Composable
fun getStateOfDataStreamViewParam(
    gitHubUsersFlow: StateFlow<List<GitHubUserUi>>,
    onItemClicked: (GitHubUserUi) -> Unit,
    onLoadPrev: () -> Unit,
    onLoadNext: () -> Unit,
): DataStreamViewParam {

    val users: List<GitHubUserUi> by gitHubUsersFlow.collectAsStateWithLifecycle()

    return DataStreamViewParam(
        gitHubUsers = users,
        onItemClicked = onItemClicked,
        onLoadPrev = onLoadPrev,
        onLoadNext = onLoadNext,
    )
}

@Composable
@Preview
fun DataStreamViewPreview() {

    var status by remember { mutableStateOf("None") }

    val param = getStateOfDataStreamViewParam(
        gitHubUsersFlow = MutableStateFlow(gitHubUsersUiFake),
        onItemClicked = { status = "Clicked on ${it.login}" },
        onLoadPrev = { status = "on load prev" },
        onLoadNext = { status = "on load next" },
    )

    MyTheme {
        Box {
            DataStreamView(
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