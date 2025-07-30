package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.di

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository.KmcDataRepository
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository.KmcDataRepositoryReal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class KmcDataModuleReal {

    @Binds
    abstract fun bindKmcDataRepository(
        repository: KmcDataRepositoryReal
    ): KmcDataRepository
}