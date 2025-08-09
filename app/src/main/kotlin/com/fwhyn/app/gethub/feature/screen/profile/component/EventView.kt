package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.Grey50
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.profile.model.GitHubEventUi
import com.fwhyn.app.gethub.feature.screen.profile.model.gitHubEventUiFake
import com.yeocak.timelineview.TimelineView

@Composable
fun EventView(
    modifier: Modifier,
    param: EventViewParam,
) {
    Column(
        modifier = modifier,
    ) {
        var nodeModifier by remember { mutableStateOf(Modifier.height(0.dp)) }
        Row {
            TimelineView.SingleNode(
                modifier = nodeModifier,
                color = MaterialTheme.colorScheme.primary,
                nodeType = param.nodeType,
                nodeSize = 25f,
                isChecked = true,
                isDashed = false
            )

            MySpacer(4.dp)
            val localDensity = (LocalDensity.current)
            Column(
                Modifier.onGloballyPositioned(
                    onGloballyPositioned = { coordinates ->
                        val itemHeight = with(localDensity) { coordinates.size.height.toDp() }
                        nodeModifier = Modifier.height(itemHeight)
                    }
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .padding(6.dp),
                ) {
                    Text(
                        text = param.event.type,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    MySpacer(4.dp)
                    Row {
                        Text(
                            modifier = Modifier.weight(2f),
                            text = stringResource(R.string.repository) +
                                    stringResource(R.string.colon) +
                                    param.event.repoName,
                            fontStyle = FontStyle.Italic,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.id) +
                                    stringResource(R.string.colon) +
                                    param.event.id,
                            textAlign = TextAlign.Right,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    MySpacer(8.dp)
                    Text(
                        text = param.event.createdAt,
                        color = Grey50,
                        textAlign = TextAlign.Right,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (param.nodeType != TimelineView.NodeType.LAST) {
                    MySpacer(2.dp)
                    HorizontalDivider(thickness = 1.dp)
                    MySpacer(2.dp)
                }
            }
        }
    }
}

data class EventViewParam(
    val event: GitHubEventUi,
    val nodeType: TimelineView.NodeType,
) {
    companion object {
        fun default(
            event: GitHubEventUi = GitHubEventUi.default(),
            nodeType: TimelineView.NodeType = TimelineView.NodeType.MIDDLE,
        ) = EventViewParam(
            event = event,
            nodeType = nodeType
        )
    }
}

@Preview
@Composable
fun EventPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EventView(
                modifier = Modifier
                    .fillMaxWidth(),
                param = EventViewParam.default(
                    event = gitHubEventUiFake
                )
            )
        }

    }
}