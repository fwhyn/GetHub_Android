package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.lib.baze.retrofit.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class RetrofitGitHubDiMain {

    @Provides
    @GitHubApi
    fun retrofitBuilder(
        @GitHubApi baseUrl: HttpUrl,
    ): RetrofitBuilder {
        val builder = RetrofitProvider.getCustomBuilder(baseUrl)

        return builder
    }

    @Provides
    @GitHubApi
    fun retrofit(
        @GitHubApi builder: RetrofitBuilder,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        builder.addBearerAuth {
            val token = authTokenLocalDataSource.token?.value ?: ""
            token
        }

        return builder.build()
    }
}