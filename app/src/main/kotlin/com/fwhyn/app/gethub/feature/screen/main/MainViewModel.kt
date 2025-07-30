package com.fwhyn.app.gethub.feature.screen.main

import androidx.lifecycle.ViewModel
import com.fwhyn.lib.baze.compose.helper.ActivityRetainedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val activityRetainedState: ActivityRetainedState,
) : ViewModel()