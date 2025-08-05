package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties

abstract class ProfileVmInterface : ViewModel() {

    abstract val properties: ProfileProperties

    abstract fun onUpdateUserName(data: String)
    open fun onLoadNextRepos() {}
    open fun onLoadNextEvents() {}
}