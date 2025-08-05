package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubEventUi
import com.fwhyn.app.gethub.feature.screen.profile.model.gitHubEventsUiFake
import com.yeocak.timelineview.TimelineView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EventsView(
    modifier: Modifier,
    param: EventsViewParam,
) {
    Column(
        modifier = modifier
    ) {
        // TODO when list empty
        // TODO when get list error
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
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection),
            state = listState
        ) {
            itemsIndexed(param.events) { index, event ->
                val nodeType = when (index) {
                    0 -> TimelineView.NodeType.FIRST
                    param.events.lastIndex -> TimelineView.NodeType.LAST
                    else -> TimelineView.NodeType.MIDDLE
                }

                val eventViewParam = EventViewParam(
                    event = event,
                    nodeType = nodeType,
                )

                EventView(
                    modifier = Modifier.fillMaxWidth(),
                    param = eventViewParam,
                )
            }
        }
    }
}

data class EventsViewParam(
    val events: List<GitHubEventUi>,
    val onLoadPrev: () -> Unit,
    val onLoadNext: () -> Unit,
) {
    companion object {
        fun default(
            events: List<GitHubEventUi> = listOf(GitHubEventUi.default()),
            onLoadPrev: () -> Unit = {},
            onLoadNext: () -> Unit = {},
        ) = EventsViewParam(
            events = events,
            onLoadPrev = onLoadPrev,
            onLoadNext = onLoadNext
        )
    }
}

val eventsViewParamFake = EventsViewParam(
    events = gitHubEventsUiFake,
    onLoadPrev = { /* Handle load previous */ },
    onLoadNext = { /* Handle load next */ }
)

@Composable
fun getStateOfDataStreamViewParam(
    eventsFlow: StateFlow<List<GitHubEventUi>>,
    onLoadPrev: () -> Unit,
    onLoadNext: () -> Unit,
): EventsViewParam {
    val events: List<GitHubEventUi> by eventsFlow.collectAsStateWithLifecycle()

    return EventsViewParam(
        events = events,
        onLoadPrev = onLoadPrev,
        onLoadNext = onLoadNext,
    )
}

@Composable
@Preview
fun EventsPreview() {
    val param = getStateOfDataStreamViewParam(
        eventsFlow = MutableStateFlow(gitHubEventsUiFake),
        onLoadPrev = { /* Handle load previous */ },
        onLoadNext = { /* Handle load next */ }
    )

    MyTheme {
        EventsView(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .fillMaxSize()
                .padding(4.dp),
            param = param
        )
    }
}