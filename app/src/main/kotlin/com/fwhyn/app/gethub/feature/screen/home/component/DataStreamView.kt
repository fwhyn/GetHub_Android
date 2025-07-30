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
import com.fwhyn.app.gethub.feature.screen.home.model.KmcUi
import com.fwhyn.app.gethub.feature.screen.home.model.RespirationRateUi
import com.fwhyn.app.gethub.feature.screen.home.model.SpoO2Ui
import com.fwhyn.app.gethub.feature.screen.home.model.TemperatureUi
import com.fwhyn.app.gethub.feature.screen.home.model.kmcUiListFake
import com.yeocak.timelineview.TimelineView
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
            itemsIndexed(param.kmcUiList) { index, kmcUi ->
                val nodeType = when (index) {
                    0 -> TimelineView.NodeType.FIRST
                    param.kmcUiList.lastIndex -> TimelineView.NodeType.LAST
                    else -> TimelineView.NodeType.MIDDLE
                }

                val kmcUiViewParam = KmcUiViewParam(
                    nodeType = nodeType,
                    kmcUi = kmcUi,
                )

                KmcUiView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(defaultKmcUiViewHeight),
                    param = kmcUiViewParam,
                )
            }
        }
    }
}

data class DataStreamViewParam(
    val kmcUiList: List<KmcUi>,
) {
    companion object {
        fun default(
            kmcUiList: List<KmcUi> = listOf(
                KmcUi.default(
                    spoO2Ui = SpoO2Ui.default(),
                    temperatureUi = TemperatureUi.default(),
                    respirationRateUi = RespirationRateUi.default()
                )
            ),
        ) = DataStreamViewParam(kmcUiList = kmcUiList)
    }
}

@Composable
fun getStateOfDataStreamViewParam(
    kmcUiListFlow: StateFlow<List<KmcUi>>,
): DataStreamViewParam {

    val kmcList: List<KmcUi> by kmcUiListFlow.collectAsStateWithLifecycle()

    return DataStreamViewParam(
        kmcUiList = kmcList
    )
}

@Composable
@Preview
fun DataStreamViewPreview() {
    val param = getStateOfDataStreamViewParam(
        kmcUiListFlow = MutableStateFlow(kmcUiListFake)
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