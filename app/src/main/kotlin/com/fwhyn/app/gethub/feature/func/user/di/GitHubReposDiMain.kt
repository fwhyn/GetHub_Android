package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di.RetrofitGitHubDiReal.GitHubApi
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposRemoteDataSource
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubReposRepositoryMain
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubReposDiMain {

    @Provides
    fun provideGitHubReposRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubReposRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubReposRemoteDataSource::class.java
        ).create()
    }

    @Provides
    fun bindGetGitHubReposRepository(
        repository: GetGitHubReposRepositoryMain,
    ): GetGitHubReposRepository {
        return repository
    }
}