package com.fwhyn.app.gethub.feature.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.fwhyn.app.gethub.common.ui.component.MySearchBar
import com.fwhyn.app.gethub.common.ui.component.MySearchBarParam
import com.fwhyn.app.gethub.common.ui.component.MySpacer
import com.fwhyn.app.gethub.common.ui.component.TopBar
import com.fwhyn.app.gethub.common.ui.component.TopBarParam
import com.fwhyn.app.gethub.common.ui.component.getStateOfMySearchBarParam
import com.fwhyn.app.gethub.common.ui.component.getStateOfTopBarParam
import com.fwhyn.app.gethub.common.ui.config.MyTheme
import com.fwhyn.app.gethub.common.ui.config.TopBarHeight
import com.fwhyn.app.gethub.feature.screen.home.component.GitHubUsersView
import com.fwhyn.app.gethub.feature.screen.home.component.GitHubUsersViewParam
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManager
import com.fwhyn.app.gethub.feature.screen.home.component.HomeStringManagerMain
import com.fwhyn.app.gethub.feature.screen.home.component.getStateOfGitHubUsersViewParam
import com.fwhyn.app.gethub.feature.screen.home.model.HomeEvent
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.app.gethub.feature.screen.home.model.HomeState
import com.fwhyn.app.gethub.feature.screen.home.model.homePropertiesFake
import com.fwhyn.app.gethub.feature.screen.profile.navigateToProfileScreen
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
            stringManager = HomeStringManagerMain(LocalContext.current),
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
    // TODO add search bar
    // TODO add reload button when error occurs
    // ----------------------------------------------------------------
    LaunchedEffect(Unit) {
        vm.properties.event.collect { event ->
            when (event) {
                is HomeEvent.Notify -> activityState.notification.showSnackbar(stringManager.getString(event.code))
                is HomeEvent.GoToProfile -> activityState.navigation.navigateToProfileScreen(event.user)
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
    val topBarParam = getStateOfTopBarParam(title = stringResource(R.string.home_title))

    val gitHubUsersViewParam = getStateOfGitHubUsersViewParam(
        gitHubUsersFlow = vm.properties.gitHubUsers,
        onItemClicked = { vm.onGoToProfile(it.login) },
        onLoadPrev = {}, // do nothing
        onLoadNext = vm::onLoadNext
    )

    val searchBarParam = getStateOfMySearchBarParam(
        querySuggestions = vm.properties.gitHubUsers,
        queryFlow = vm.properties.query,
        onQueryChange = vm::onQueryChange,
        onSearch = vm::onSearch,
        onClearQuery = vm::onClearQuery,
    )

    val param = HomeViewParam(
        topBarParam = topBarParam,
        gitHubUsersViewParam = gitHubUsersViewParam,
        searchBarParam = searchBarParam,
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

    // TODO add landscape view
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
            topBarParam = TopBarParam(title = stringResource(R.string.home_title))
        )

        MySearchBar(
            modifier = Modifier.fillMaxWidth(),
            param = param.searchBarParam
        )

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = stringResource(R.string.github_users) + " (${param.gitHubUsersViewParam.gitHubUsers.size})",
            )

            MySpacer(4.dp)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                GitHubUsersView(
                    modifier = Modifier.padding(8.dp),
                    param = param.gitHubUsersViewParam
                )
            }
        }
    }
}

data class HomeViewParam(
    val topBarParam: TopBarParam,
    val gitHubUsersViewParam: GitHubUsersViewParam,
    val searchBarParam: MySearchBarParam,
)

@DevicePreviews
@Composable
fun HomeScreenPreview() {
    MyTheme {
        HomeScreen(
            activityState = rememberActivityState(),
            stringManager = HomeStringManagerMain(LocalContext.current),
            vm = object : HomeVmInterface() {
                override val properties: HomeProperties
                    get() = homePropertiesFake
            },
        )
    }
}