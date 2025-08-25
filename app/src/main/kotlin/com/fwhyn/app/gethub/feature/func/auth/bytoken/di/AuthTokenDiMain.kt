package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSourceMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.AuthUserRemoteDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCase
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCaseMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCase
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCaseMain
import com.fwhyn.lib.baze.retrofit.api.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
open class AuthTokenDiMain {

    @Provides
    fun authTokenLocalDataSource(
        dataSource: AuthTokenLocalDataSourceMain,
    ): AuthTokenLocalDataSource {
        return dataSource
    }

    @Provides
    fun authUserRemoteDataSource(
        @GitHubApi retrofit: Retrofit,
    ): AuthUserRemoteDataSource {
        return RetrofitApiService(
            retrofit = retrofit,
            cls = AuthUserRemoteDataSource::class.java
        ).create()
    }

    @Provides
    fun authTokenRepository(
        dataSource: AuthTokenRepositoryMain,
    ): AuthTokenRepository {
        return dataSource
    }

    @Provides
    fun authUserRepository(
        dataSource: AuthUserRepositoryMain,
    ): AuthUserRepository {
        return dataSource
    }

    @Provides
    fun loginByTokenUseCase(
        useCase: LoginByTokenUseCaseMain,
    ): LoginByTokenUseCase {
        return useCase
    }

    @Provides
    fun logoutUseCase(
        useCase: LogoutUseCaseMain,
    ): LogoutUseCase {
        return useCase
    }
}