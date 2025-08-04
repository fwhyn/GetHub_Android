package com.fwhyn.app.gethub.feature.screen.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme

@Composable
fun ProfileViewSection2(
    modifier: Modifier,
    param: ProfileViewSection2Param,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.events),
            fontWeight = FontWeight.Bold
        )

        MySpacer(8.dp)
        EventsView(
            modifier = Modifier.fillMaxSize(),
            param = param.eventsViewParam
        )
    }
}

data class ProfileViewSection2Param(
    val eventsViewParam: EventsViewParam,
) {
    companion object {
        fun default(
            eventsViewParam: EventsViewParam = EventsViewParam.default(),
        ): ProfileViewSection2Param {
            return ProfileViewSection2Param(
                eventsViewParam = eventsViewParam,
            )
        }
    }
}

val profileViewSection2ParamFake = ProfileViewSection2Param(
    eventsViewParam = eventsViewParamFake,
)

@Preview
@Composable
fun ProfileViewSection2Preview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            ProfileViewSection2(
                modifier = Modifier.fillMaxWidth(),
                param = profileViewSection2ParamFake
            )
        }
    }
}