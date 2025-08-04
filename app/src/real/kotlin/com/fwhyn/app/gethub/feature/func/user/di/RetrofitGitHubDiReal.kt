package com.fwhyn.app.gethub.feature.func.user.di

import com.fwhyn.app.gethub.BuildConfig
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
    fun provideRetrofit(
        @GitHubApi baseUrl: HttpUrl,
    ): Retrofit {
        val builder = RetrofitBuilder(baseUrl)

        if (BuildConfig.DEBUG) {
            builder.enableLog()
        }

        return builder.build()
    }
}