package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
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
    fun retrofit(
        @GitHubApi baseUrl: HttpUrl,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        val builder = RetrofitProvider
            .getCustomBuilder(baseUrl)
            .addBearerAuth {
                val token = authTokenLocalDataSource.token?.value ?: ""
                token
            }

        return builder.build()
    }
}