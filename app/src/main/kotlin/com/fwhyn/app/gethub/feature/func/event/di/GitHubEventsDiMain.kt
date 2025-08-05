package com.fwhyn.app.gethub.feature.func.event.di

import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepository
import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepositoryMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GitHubEventsDiMain {

    @Binds
    abstract fun bindGetGitHubEventsRepository(
        repository: GetGitHubEventsRepositoryMain,
    ): GetGitHubEventsRepository
}