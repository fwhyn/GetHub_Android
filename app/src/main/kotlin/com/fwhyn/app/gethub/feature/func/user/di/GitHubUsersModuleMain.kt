package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.user.data.repository.GitHubUsersRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GitHubUsersRepositoryMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class GitHubUsersModuleMain {

    @Binds
    abstract fun bindGitHubUsersRepository(
        repository: GitHubUsersRepositoryMain,
    ): GitHubUsersRepository
}