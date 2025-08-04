package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
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
) {
    companion object {
        fun default(
            events: List<GitHubEventUi> = listOf(GitHubEventUi.default()),
        ) = EventsViewParam(events = events)
    }
}

val eventsViewParamFake = EventsViewParam(
    events = gitHubEventsUiFake,
)

@Composable
fun getStateOfDataStreamViewParam(
    eventsFlow: StateFlow<List<GitHubEventUi>>,
): EventsViewParam {
    val events: List<GitHubEventUi> by eventsFlow.collectAsStateWithLifecycle()

    return EventsViewParam(
        events = events
    )
}

@Composable
@Preview
fun EventsPreview() {
    val param = getStateOfDataStreamViewParam(
        eventsFlow = MutableStateFlow(gitHubEventsUiFake)
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