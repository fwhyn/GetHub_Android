package com.fwhyn.app.gethub.feature.func.event.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsRemoteDataSource
import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepository
import com.fwhyn.app.gethub.feature.func.event.data.repository.GetGitHubEventsRepositoryMain
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubEventsDiMain {

    @Provides
    fun gitHubEventsRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubEventsRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubEventsRemoteDataSource::class.java
        ).create()
    }

    @Provides
    fun getGitHubEventsRepository(
        repository: GetGitHubEventsRepositoryMain,
    ): GetGitHubEventsRepository {
        return repository
    }
}