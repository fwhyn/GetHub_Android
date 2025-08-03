package com.fwhyn.app.gethub.feature.screen.profile.model

sealed class ProfileState {
    data object Idle : ProfileState()
    data object Loading : ProfileState()
}