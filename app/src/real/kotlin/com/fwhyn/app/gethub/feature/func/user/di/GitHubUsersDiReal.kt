package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersRemoteDataSource
import com.fwhyn.app.gethub.feature.func.user.di.RetrofitGitHubDiReal.GitHubApi
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubUsersDiReal {

    @Provides
    fun provideGitHubUsersRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubUsersRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubUsersRemoteDataSource::class.java
        ).create()
    }
}