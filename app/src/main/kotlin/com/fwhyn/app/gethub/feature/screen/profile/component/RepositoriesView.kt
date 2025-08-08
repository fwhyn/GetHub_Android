package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubRepoUi
import com.fwhyn.app.gethub.feature.screen.profile.model.gitHubReposUiFake
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RepositoriesView(
    modifier: Modifier = Modifier,
    param: RepositoriesViewParam,
) {
    if (param.repos.isEmpty()) {
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
                    available.x > 0f && consumed.x == 0f -> param.onLoadPrev()
                    available.x < 0f && consumed.x == 0f -> param.onLoadNext()
                }

                return Offset.Zero
            }
        }
    }
    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier.nestedScroll(nestedScrollConnection),
        state = listState
    ) {
        itemsIndexed(param.repos) { index, repo ->
            val repositoryViewParam = RepositoryViewParam(
                repo = repo,
            )

            RepositoryView(
                modifier = Modifier.size(width = defaultRepoItemWidth, height = defaultRepoItemHeight),
                param = repositoryViewParam,
            )

            if (index < param.repos.size - 1) {
                // Add a spacer between items
                MySpacer(8.dp)
            }
        }
    }
}

val defaultRepoItemWidth = 300.dp
val defaultRepoItemHeight = 150.dp

data class RepositoriesViewParam(
    val repos: List<GitHubRepoUi>,
    val onLoadPrev: () -> Unit,
    val onLoadNext: () -> Unit,
) {
    companion object {
        fun default(
            repos: List<GitHubRepoUi> = emptyList(),
            onLoadPrev: () -> Unit = {},
            onLoadNext: () -> Unit = {},
        ): RepositoriesViewParam {
            return RepositoriesViewParam(
                repos = repos,
                onLoadPrev = onLoadPrev,
                onLoadNext = onLoadNext,
            )
        }
    }
}

val reposViewParamFake = RepositoriesViewParam(
    repos = gitHubReposUiFake,
    onLoadPrev = {},
    onLoadNext = {}
)

@Composable
fun getStateOfRepositoriesViewParam(
    reposFlow: StateFlow<List<GitHubRepoUi>>,
    onLoadPrev: () -> Unit,
    onLoadNext: () -> Unit,
): RepositoriesViewParam {
    val repos: List<GitHubRepoUi> by reposFlow.collectAsStateWithLifecycle()

    return RepositoriesViewParam(
        repos = repos,
        onLoadPrev = onLoadPrev,
        onLoadNext = onLoadNext,
    )
}

@Composable
@DevicePreviews
fun RepositoriesPreview() {
    var status by remember { mutableStateOf("None") }

    val param = getStateOfRepositoriesViewParam(
        reposFlow = MutableStateFlow(gitHubReposUiFake),
        onLoadPrev = { status = "Load Previous" },
        onLoadNext = { status = "Load Next" }
    )

    MyTheme {
        Box {
            RepositoriesView(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth()
                    .height(defaultRepoItemHeight)
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
fun RepositoriesEmptyPreview() {
    var status by remember { mutableStateOf("None") }

    val param = getStateOfRepositoriesViewParam(
        reposFlow = MutableStateFlow(emptyList()),
        onLoadPrev = { status = "Load Previous" },
        onLoadNext = { status = "Load Next" }
    )

    MyTheme {
        Box {
            RepositoriesView(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiary)
                    .fillMaxWidth()
                    .height(defaultRepoItemHeight)
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