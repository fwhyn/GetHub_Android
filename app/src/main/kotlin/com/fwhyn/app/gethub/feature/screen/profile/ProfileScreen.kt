package com.fwhyn.app.gethub.feature.screen.profile

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
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
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fwhyn.app.gethub.R
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.component.TopBar
import com.fwhyn.app.gethub.common.ui.component.TopBarParam
import com.fwhyn.app.gethub.common.ui.component.getStateOfTopBarParam
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.common.ui.config.TopBarHeight
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileStringManager
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileStringManagerMain
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileViewSection1
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileViewSection1Param
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileViewSection2
import com.fwhyn.app.gethub.feature.screen.profile.component.ProfileViewSection2Param
import com.fwhyn.app.gethub.feature.screen.profile.component.getStateOfDataStreamViewParam
import com.fwhyn.app.gethub.feature.screen.profile.component.getStateOfRepositoriesViewParam
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileEvent
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileState
import com.fwhyn.app.gethub.feature.screen.profile.model.profilePropertiesFake
import com.fwhyn.lib.baze.compose.dialog.CircularProgressDialog
import com.fwhyn.lib.baze.compose.helper.ActivityState
import com.fwhyn.lib.baze.compose.helper.DevicePreviews
import com.fwhyn.lib.baze.compose.helper.rememberActivityState

private const val USERNAME = "userName"
const val PROFILE_ROUTE = "PROFILE_ROUTE/{$USERNAME}"

fun NavGraphBuilder.addProfileScreen(
    activityState: ActivityState,
) {
    composable(
        route = PROFILE_ROUTE,
        arguments = listOf(navArgument(USERNAME) { type = NavType.StringType })
    ) { backStack ->
        val vm = hiltViewModel<ProfileViewModel>()
        val userName = backStack.arguments?.getString(USERNAME) ?: ""
        vm.onUpdateUserName(userName)

        ProfileScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            activityState = activityState,
            stringManager = ProfileStringManagerMain(LocalContext.current),
            vm = vm
        )
    }
}

fun NavController.navigateToProfileScreen(userName: String, navOptions: NavOptions? = null) {
    this.navigate("PROFILE_ROUTE/$userName", navOptions)
}

// TODO create adaptable font size but limited to 16sp
@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    activityState: ActivityState,
    stringManager: ProfileStringManager,
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
    val topBarParam = getStateOfTopBarParam(
        title = stringResource(R.string.profile_title),
        onBack = {
            activityState.navigation.popBackStack()
        }
    )

    val userProfile by vm.properties.gitHubUserProfile.collectAsStateWithLifecycle()
    val reposViewParam = getStateOfRepositoriesViewParam(
        reposFlow = vm.properties.gitHubRepos,
        onLoadPrev = {},
        onLoadNext = vm::onLoadNextRepos,
    )
    val profileViewSection1Param = ProfileViewSection1Param(
        userProfile = userProfile,
        reposViewParam = reposViewParam,
    )

    val eventsViewParam = getStateOfDataStreamViewParam(
        eventsFlow = vm.properties.gitHubEvents,
        onLoadPrev = {},
        onLoadNext = vm::onLoadNextEvents,
    )
    val profileViewSection2Param = ProfileViewSection2Param(
        eventsViewParam = eventsViewParam
    )

    val param = ProfileViewParam(
        topBarParam = topBarParam,
        profileViewSection1Param = profileViewSection1Param,
        profileViewSection2Param = profileViewSection2Param
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
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeProfileView(
                modifier = modifier,
                param = param
            )
        }

        else -> {
            PortraitProfileView(
                modifier = modifier,
                param = param
            )
        }
    }


}

@Composable
fun PortraitProfileView(
    modifier: Modifier = Modifier,
    param: ProfileViewParam,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(
            modifier = Modifier.height(TopBarHeight),
            topBarParam = TopBarParam.default(
                title = stringResource(R.string.profile_title),
                onBack = {}
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            ProfileViewSection1(
                modifier = Modifier.fillMaxWidth(),
                param = param.profileViewSection1Param
            )

            MySpacer(8.dp)
            ProfileViewSection2(
                modifier = Modifier.fillMaxWidth(),
                param = param.profileViewSection2Param
            )
        }
    }
}

@Composable
fun LandscapeProfileView(
    modifier: Modifier = Modifier,
    param: ProfileViewParam,
) {
    Column(
        modifier = modifier
    ) {
        TopBar(
            modifier = Modifier.height(TopBarHeight),
            topBarParam = TopBarParam.default(
                title = stringResource(R.string.profile_title),
                onBack = {}
            )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            ProfileViewSection1(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                param = param.profileViewSection1Param
            )

            MySpacer(8.dp)
            VerticalDivider()

            MySpacer(8.dp)
            ProfileViewSection2(
                modifier = Modifier.weight(1f),
                param = param.profileViewSection2Param
            )
        }
    }
}

data class ProfileViewParam(
    val topBarParam: TopBarParam,
    val profileViewSection1Param: ProfileViewSection1Param,
    val profileViewSection2Param: ProfileViewSection2Param,
)

@DevicePreviews
@Composable
fun ProfileScreenPreview() {
    MyTheme {
        ProfileScreen(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            activityState = rememberActivityState(),
            stringManager = ProfileStringManagerMain(LocalContext.current),
            vm = object : ProfileVmInterface() {
                override val properties: ProfileProperties
                    get() = profilePropertiesFake

                override fun onUpdateUserName(data: String) {
                    // No-op for preview
                }
            },
        )
    }
}