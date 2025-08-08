package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.lib.baze.retrofit.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ActivityRetainedComponent::class)
class RetrofitGitHubDiReal {

    @Qualifier
    annotation class GitHubApi

    @Provides
    @GitHubApi
    fun provideBaseUrl(): HttpUrl {
        return "https://api.github.com/".toHttpUrl()
    }

    @Provides
    @GitHubApi
    fun provideRetrofitBuilder(
        @GitHubApi baseUrl: HttpUrl,
    ): RetrofitBuilder {
        val builder = RetrofitProvider.getCustomBuilder(baseUrl)

        return builder
    }

    @Provides
    @GitHubApi
    fun provideRetrofit(
        @GitHubApi builder: RetrofitBuilder,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        builder.addBearerAuth { authTokenLocalDataSource.token?.value ?: "" }

        return builder.build()
    }
}