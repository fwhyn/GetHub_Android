package com.fwhyn.app.gethub.feature.screen.home

import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.home.model.HomeProperties

abstract class HomeVmInterface : ViewModel() {

    abstract val properties: HomeProperties

    open fun onGoToProfile(user: String) {}
    open fun onLogout() {}
    open fun onLoadNext() {}
    open fun onQueryChange(query: String) {}
    open fun onSearch(query: String) {}
    open fun onClearQuery() {}
}