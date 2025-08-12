package com.fwhyn.app.gethub.feature.screen.home

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties
import com.fwhyn.lib.baze.compose.model.CommonProperties

abstract class HomeVmInterface : ViewModel() {

    abstract val commonProp: CommonProperties
    abstract val properties: HomeProperties

    open fun onGoToProfile(user: String) {}
    open fun onLogout() {}
    open fun onLoadNext() {}
    open fun onQueryChange(query: String) {}
    open fun onSearch(query: String) {}
    open fun onClearQuery() {}
}