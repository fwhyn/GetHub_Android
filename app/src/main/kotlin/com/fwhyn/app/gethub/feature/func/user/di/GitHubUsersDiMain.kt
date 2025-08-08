package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di.RetrofitGitHubDiReal.GitHubApi
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUsersRepositoryMain
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubUsersDiMain {

    @Provides
    fun provideGitHubUsersRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubUsersRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubUsersRemoteDataSource::class.java
        ).create()
    }

    @Provides
    fun bindGetGitHubUsersRepository(
        repository: GetGitHubUsersRepositoryMain,
    ): GetGitHubUsersRepository {
        return repository
    }
}