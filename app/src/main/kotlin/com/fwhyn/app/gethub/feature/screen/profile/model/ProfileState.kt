package com.fwhyn.app.gethub.feature.screen.profile.model

sealed class ProfileState {
    data object Loading : ProfileState()
}