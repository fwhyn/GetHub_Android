package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di.RetrofitGitHubDiReal.GitHubApi
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceReal
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.AuthUserRemoteDataSource
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class AuthTokenDiReal {

    @Provides
    fun authTokenLocalDataSource(
        dataSource: AuthTokenLocalDataSourceReal,
    ): AuthTokenLocalDataSource {
        return dataSource
    }
}