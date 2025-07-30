package com.fwhyn.app.gethub.feature.screen.home

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.fwhyn.app.gethub.feature.screen.home.model.HomeParam

abstract class HomeVmInterface : ViewModel() {

    abstract val param: HomeParam

    open fun onLogout() {}
    open fun onConnectOrDisconnect() {}
    open fun onExportData() {}
    open fun onCreateFileResult(result: ActivityResult) {}
}