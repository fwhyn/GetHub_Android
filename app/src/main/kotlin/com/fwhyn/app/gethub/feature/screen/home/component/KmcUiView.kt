package com.fwhyn.app.gethub.feature.screen.home.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.feature.screen.home.model.KmcUi
import com.fwhyn.app.gethub.feature.screen.home.model.RespirationRateUi
import com.fwhyn.app.gethub.feature.screen.home.model.SpoO2Ui
import com.fwhyn.app.gethub.feature.screen.home.model.TemperatureUi
import com.yeocak.timelineview.TimelineView

@Composable
fun KmcUiView(
    modifier: Modifier,
    param: KmcUiViewParam,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            TimelineView.SingleNode(
                modifier = Modifier.padding(horizontal = 2.dp),
                color = MaterialTheme.colorScheme.primary,
                nodeType = param.nodeType,
                nodeSize = 30f,
                isChecked = true,
                isDashed = false
            )

            MySpacer(8.dp)
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .padding(6.dp),
                ) {
                    Text(
                        text = param.kmcUi.timeStamp,
                        color = MaterialTheme.colorScheme.secondary,
                    )

                    MySpacer(6.dp)
                    Text(
                        text = "SpO2: ${param.kmcUi.spoO2.data}${param.kmcUi.spoO2.unit}",
                    )
                    Text(
                        text = "Temperature: ${param.kmcUi.temperature.data}${param.kmcUi.temperature.unit}",
                    )
                    Text(
                        text = "Respiration Rate: ${param.kmcUi.respirationRate.data}${param.kmcUi.respirationRate.unit}",
                    )
                }

                if (param.nodeType != TimelineView.NodeType.LAST) {
                    MySpacer(3.dp)
                    HorizontalDivider(thickness = 1.dp)
                }
            }
        }
    }
}

val defaultKmcUiViewHeight = 92.dp

data class KmcUiViewParam(
    val kmcUi: KmcUi,
    val nodeType: TimelineView.NodeType,
) {
    companion object {
        fun default(
            kmcUi: KmcUi = KmcUi.default(),
            nodeType: TimelineView.NodeType = TimelineView.NodeType.MIDDLE,
        ) = KmcUiViewParam(
            kmcUi = kmcUi,
            nodeType = nodeType
        )
    }
}

@Preview
@Composable
fun KmcUiViewPreview() {
    MyTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            KmcUiView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(defaultKmcUiViewHeight),
                param = KmcUiViewParam.default(
                    kmcUi = KmcUi.default(
                        timeStamp = "2023-10-01 12:00:00",
                        spoO2Ui = SpoO2Ui.default(data = 98),
                        temperatureUi = TemperatureUi.default(data = 36.5f),
                        respirationRateUi = RespirationRateUi.default(data = 16)
                    )
                )
            )
        }

    }
}