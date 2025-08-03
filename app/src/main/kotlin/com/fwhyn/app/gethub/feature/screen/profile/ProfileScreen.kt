package com.fwhyn.app.gethub.feature.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.fwhyn.app.gethub.common.ui.component.TopBar
import com.fwhyn.app.gethub.common.ui.component.TopBarParam
import com.fwhyn.app.gethub.common.ui.component.getStateOfTopBarHomeParam
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.common.ui.config.TopBarHeight
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManager
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManagerMain
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileEvent
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileState
import com.fwhyn.app.gethub.feature.screen.profile.model.profilePropertiesFake
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState

const val PROFILE_ROUTE = "PROFILE_ROUTE"

fun NavGraphBuilder.addProfileScreen(
    activityState: ActivityState,
) {
    composable(PROFILE_ROUTE) {
        ProfileScreen(
            modifier = Modifier.fillMaxSize(),
            activityState = activityState,
            stringManager = HomeStringManagerMain(LocalContext.current),
            vm = hiltViewModel<ProfileViewModel>()
        )
    }
}

fun NavController.navigateToProfileScreen(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_ROUTE, navOptions)
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    activityState: ActivityState,
    stringManager: HomeStringManager,
    vm: ProfileVmInterface,
) {
    // TODO add reload button when error occurs
    // ----------------------------------------------------------------
    LaunchedEffect(Unit) {
        vm.properties.event.collect { event ->
            when (event) {
                is ProfileEvent.Notify -> activityState.notification.showSnackbar(stringManager.getString(event.code))
                is ProfileEvent.GoToHome -> {} // TODO Handle go to home
            }
        }
    }

    // ----------------------------------------------------------------
    val state by vm.properties.state.collectAsStateWithLifecycle()
    when (state) {
        ProfileState.Idle -> {} // do nothing
        ProfileState.Loading -> CircularProgressDialog()
    }

    // ----------------------------------------------------------------
    val topBarParam = getStateOfTopBarHomeParam(
        title = stringResource(R.string.profile_title),
        onBack = {
            activityState.navigation.popBackStack()
        }
    )

    val param = ProfileViewParam(
        topBarParam = topBarParam,
    )

    ProfileView(
        modifier = modifier,
        param = param,
    )
}

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    param: ProfileViewParam,
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
    param: ProfileViewParam,
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
            }
        }
    }
}

data class ProfileViewParam(
    val topBarParam: TopBarParam,
)

@DevicePreviews
@Composable
fun ProfileScreenPreview() {
    MyTheme {
        ProfileScreen(
            activityState = rememberActivityState(),
            stringManager = HomeStringManagerMain(LocalContext.current),
            vm = object : ProfileVmInterface() {
                override val properties: ProfileProperties
                    get() = profilePropertiesFake
            },
        )
    }
}