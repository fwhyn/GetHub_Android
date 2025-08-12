package com.fwhyn.app.gethub.feature.screen.profile

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.profile.model.ProfileProperties
import com.fwhyn.lib.baze.compose.model.CommonProperties

abstract class ProfileVmInterface : ViewModel() {

    abstract val commonProp: CommonProperties
    abstract val properties: ProfileProperties

    abstract fun onUpdateUserName(data: String)
    open fun onLoadNextRepos() {}
    open fun onLoadNextEvents() {}
}