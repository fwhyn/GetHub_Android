package com.fwhyn.app.gethub.feature.screen.home.model

sealed class HomeState {
    data object Idle : HomeState()
    data object Loading : HomeState()
}