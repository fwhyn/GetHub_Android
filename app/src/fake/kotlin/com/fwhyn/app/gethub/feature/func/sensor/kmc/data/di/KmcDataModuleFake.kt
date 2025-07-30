package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.di

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository.KmcDataRepository
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository.KmcDataRepositoryFake
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class KmcDataModuleFake {

    @Binds
    abstract fun bindKmcDataRepository(
        repository: KmcDataRepositoryFake
    ): KmcDataRepository
}