package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepositoryMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GitHubUsersModuleMain {

    @Binds
    abstract fun bindGetGitHubUsersRepository(
        repository: GetGitHubUsersRepositoryMain,
    ): GetGitHubUsersRepository
}