package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepositoryMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GitHubUserProfileDiMain {

    @Binds
    abstract fun bindGetGitHubUserProfileRepository(
        repository: GetGitHubUserProfileRepositoryMain,
    ): GetGitHubUserProfileRepository
}