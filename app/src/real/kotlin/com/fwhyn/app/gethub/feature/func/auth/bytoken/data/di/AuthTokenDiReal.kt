package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di.RetrofitGitHubDiReal.GitHubRetrofit
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
    fun provideAuthTokenLocalDataSource(
        dataSource: AuthTokenLocalDataSourceReal,
    ): AuthTokenLocalDataSource {
        return dataSource
    }

    @Provides
    fun provideAuthUserRemoteDataSource(
        @GitHubRetrofit retrofit: Retrofit,
    ): AuthUserRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = AuthUserRemoteDataSource::class.java
        ).create()
    }
}