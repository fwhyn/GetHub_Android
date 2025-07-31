package com.fwhyn.app.gethub.feature.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.storage.saf.getFileCreatorLauncher
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.component.TopBar
import com.fwhyn.app.gethub.common.ui.component.TopBarParam
import com.fwhyn.app.gethub.common.ui.component.getStateOfTopBarHomeParam
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.common.ui.config.TopBarHeight
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper.ExportExcelUtil
import com.fwhyn.app.gethub.feature.screen.home.component.ConnectDisconnectBtn
import com.fwhyn.app.gethub.feature.screen.home.component.ConnectDisconnectBtnParam
import com.fwhyn.app.gethub.feature.screen.home.component.DataStreamView
import com.fwhyn.app.gethub.feature.screen.home.component.DataStreamViewParam
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManager
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManagerImpl
import com.fwhyn.app.gethub.feature.screen.home.component.getStateOfConnectDisconnectBtnParam
import com.fwhyn.app.gethub.feature.screen.home.component.getStateOfDataStreamViewParam
import com.fwhyn.app.gethub.feature.screen.home.helper.OpenSafCode
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
import com.fwhyn.app.gethub.feature.screen.home.model.homePropertiesFake
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState

const val HOME_ROUTE = "HOME_ROUTE"

fun NavGraphBuilder.addHomeScreen(
    activityState: ActivityState,
) {
    composable(HOME_ROUTE) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            activityState = activityState,
            stringManager = HomeStringManagerImpl(LocalContext.current),
            vm = hiltViewModel<HomeViewModel>()
        )
    }
}

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    activityState: ActivityState,
    stringManager: HomeStringManager,
    vm: HomeVmInterface,
) {
    val safFileCreator = getFileCreatorLauncher(onResult = vm::onCreateFileResult)

    // ----------------------------------------------------------------
    LaunchedEffect(Unit) {
        vm.properties.event.collect { event ->
            when (event) {
                is HomeEvent.OnNotify -> activityState.notification.showSnackbar(stringManager.getString(event.code))
                is HomeEvent.OpenSaf -> when (event.code) {
                    OpenSafCode.ExportKmcList -> ExportExcelUtil.requestToCreateWorkBook(safFileCreator)
                }
            }
        }
    }

    // ----------------------------------------------------------------
    val state by vm.properties.state.collectAsStateWithLifecycle()
    when (state) {
        HomeState.Idle -> {} // do nothing
        HomeState.Loading -> CircularProgressDialog()
    }

    // ----------------------------------------------------------------
    val topBarParam = getStateOfTopBarHomeParam(
        title = stringResource(R.string.home_title),
        onBack = {
            activityState.navigation.popBackStack()
        }
    )

    val dataStreamViewParam = getStateOfDataStreamViewParam(
        kmcUiListFlow = vm.properties.kmcUiList
    )

    val connectDisconnectBtnParam = getStateOfConnectDisconnectBtnParam(
        isConnected = vm.properties.isRealTimeData,
        onClick = vm::onConnectOrDisconnect
    )

    val param = HomeViewParam(
        topBarParam = topBarParam,
        dataStreamViewParam = dataStreamViewParam,
        connectDisconnectBtnParam = connectDisconnectBtnParam,
        onExportData = vm::onExportData,
    )

    HomeView(
        modifier = modifier,
        param = param,
    )
}

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    param: HomeViewParam,
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
//        Configuration.ORIENTATION_LANDSCAPE -> {
//            LandscapeHomeView(
//                modifier = modifier,
//                param = param
//            )
//        }

        else -> {
            PortraitHomeView(
                modifier = modifier,
                param = param
            )
        }
    }


}

@Composable
fun PortraitHomeView(
    modifier: Modifier = Modifier,
    param: HomeViewParam,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(
            modifier = Modifier.height(TopBarHeight),
            topBarParam = TopBarParam.default(
                title = stringResource(R.string.home_title),
                onBack = {}
            )
        )

        Box(
            modifier = modifier.weight(1f)
        ) {
//        LogoutButton(
//            modifier = Modifier.align(Alignment.TopEnd),
//            onClick = onLogout,
//        )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {

                Text(
                    text = stringResource(R.string.kmc_data)
                )

                MySpacer(4.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .background(color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    DataStreamView(
                        modifier = Modifier.padding(8.dp),
                        param = param.dataStreamViewParam
                    )
                }

                MySpacer(8.dp)
                Row {
                    MySpacer(50.dp)
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = param.onExportData
                    ) {
                        Text(text = stringResource(R.string.export))
                    }

                    MySpacer(8.dp)
                    ConnectDisconnectBtn(
                        modifier = Modifier.weight(1f),
                        param = param.connectDisconnectBtnParam
                    )
                }
            }
        }
    }
}

data class HomeViewParam(
    val topBarParam: TopBarParam,
    val dataStreamViewParam: DataStreamViewParam,
    val connectDisconnectBtnParam: ConnectDisconnectBtnParam,
    val onExportData: () -> Unit,
)

@DevicePreviews
@Composable
fun HomeScreenPreview() {
    MyTheme {
        HomeScreen(
            activityState = rememberActivityState(),
            stringManager = HomeStringManagerImpl(LocalContext.current),
            vm = object : HomeVmInterface() {
                override val properties: HomeProperties
                    get() = homePropertiesFake
            },
        )
    }
}