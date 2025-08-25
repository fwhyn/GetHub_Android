package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
open class TestDiMain {

    @Provides
    fun test() = "test"
}