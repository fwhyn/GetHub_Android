package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import com.fwhyn.lib.baze.retrofit.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubApiDiReal {

    @Provides
    @GitHubApi
    fun baseUrl(): HttpUrl {
        return "https://api.github.com/".toHttpUrl()
    }
}