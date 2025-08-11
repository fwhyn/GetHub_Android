package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubApiDiFake {

    @Provides
    @GitHubApi
    fun baseUrl(): HttpUrl {
        return "https://api.github.com/".toHttpUrl()
    }
}