package com.fwhyn.app.gethub.common.di

import com.fwhyn.lib.baze.compose.helper.ActivityRetainedState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@InstallIn(ActivityRetainedComponent::class)
@Module
class ActivityRetainedDi {

    @Provides
    @ActivityRetainedScoped
    fun activityRetainedState(): ActivityRetainedState = ActivityRetainedState()
}