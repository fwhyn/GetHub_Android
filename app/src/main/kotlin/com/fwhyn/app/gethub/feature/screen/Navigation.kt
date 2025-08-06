package com.fwhyn.app.gethub.feature.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.fwhyn.app.gethub.feature.screen.home.HOME_ROUTE
import com.fwhyn.app.gethub.feature.screen.home.addHomeScreen
import com.fwhyn.app.gethub.feature.screen.profile.addProfileScreen
import com.fwhyn.lib.baze.compose.helper.ActivityState

@Composable
fun NavigationHost(
    activityState: ActivityState,
) {
    NavHost(
        navController = activityState.navigation,
        startDestination = HOME_ROUTE
    ) {
        addHomeScreen(
            activityState = activityState,
        )
        addProfileScreen(
            activityState = activityState
        )
    }
}