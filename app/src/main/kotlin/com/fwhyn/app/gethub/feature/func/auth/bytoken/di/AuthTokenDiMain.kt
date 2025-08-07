package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepositoryMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCase
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LoginByTokenUseCaseMain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCase
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase.LogoutUseCaseMain
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AuthTokenDiMain {

    @Binds
    abstract fun bindAuthTokenRepository(
        dataSource: AuthTokenRepositoryMain,
    ): AuthTokenRepository

    @Binds
    abstract fun bindAuthUserRepository(
        dataSource: AuthUserRepositoryMain,
    ): AuthUserRepository

    @Binds
    abstract fun bindLoginByTokenUseCase(
        useCase: LoginByTokenUseCaseMain,
    ): LoginByTokenUseCase

    @Binds
    abstract fun bindLogoutUseCase(
        useCase: LogoutUseCaseMain,
    ): LogoutUseCase
}