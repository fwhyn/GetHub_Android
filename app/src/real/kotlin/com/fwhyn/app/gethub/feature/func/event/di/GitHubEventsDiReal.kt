package com.fwhyn.app.gethub.feature.func.event.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di.RetrofitGitHubDiReal.GitHubApi
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsRemoteDataSource
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubEventsDiReal {

    @Provides
    fun provideGitHubEventsRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubEventsRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubEventsRemoteDataSource::class.java
        ).create()
    }
}