package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.HttpUrl

@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubApiDiFake {

    @Provides
    @GitHubApi
    fun baseUrl(): HttpUrl {
        var httpUrl: HttpUrl? = null
        CoroutineScope(Dispatchers.IO).launch {
            httpUrl = MockWebServerProvider.get().url("/")
        }

        return httpUrl ?: throw IllegalStateException("MockWebServer URL is not initialized")
    }
}