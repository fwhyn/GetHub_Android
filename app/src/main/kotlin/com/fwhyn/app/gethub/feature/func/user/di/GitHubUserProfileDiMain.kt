package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUserProfileRemoteDataSource
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepository
import com.fwhyn.app.gethub.feature.func.user.data.repository.GetGitHubUserProfileRepositoryMain
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubUserProfileDiMain {

    @Provides
    fun gitHubUsersRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): GitHubUserProfileRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = GitHubUserProfileRemoteDataSource::class.java
        ).create()
    }

    @Provides
    fun gitHubUserProfileRepository(
        repository: GetGitHubUserProfileRepositoryMain,
    ): GetGitHubUserProfileRepository {
        return repository
    }
}