package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepositoryMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GitHubReposDiMain {

    @Binds
    abstract fun bindGetGitHubReposRepository(
        repository: GetGitHubReposRepositoryMain,
    ): GetGitHubReposRepository
}