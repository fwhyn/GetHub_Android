package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
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
    annotation class GitHubUrl

    @Qualifier
    annotation class GitHubRetrofit

    @Qualifier
    annotation class GitHubRetrofitWithToken

    @Provides
    @GitHubUrl
    fun provideBaseUrl(): HttpUrl {
        return "https://api.github.com/".toHttpUrl()
    }

    @Provides
    @GitHubRetrofit
    fun provideRetrofit(
        @GitHubUrl baseUrl: HttpUrl,
    ): Retrofit {
        val builder = RetrofitProvider.getCustomBuilder(baseUrl)

        return builder.build()
    }

    @Provides
    @GitHubRetrofitWithToken
    fun provideRetrofitWithToken(
        @GitHubUrl baseUrl: HttpUrl,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        val builder = RetrofitProvider
            .getCustomBuilder(baseUrl)
            .addBearerAuth { authTokenLocalDataSource.token?.value ?: "" }

        return builder.build()
    }
}