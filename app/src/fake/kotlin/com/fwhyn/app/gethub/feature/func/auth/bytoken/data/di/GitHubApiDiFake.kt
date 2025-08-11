package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.di.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.HttpUrl


@Module
@InstallIn(ActivityRetainedComponent::class)
class GitHubApiDiFake {

    @Provides
    @GitHubApi
    fun baseUrl(): HttpUrl {

        // Allow network operations on the main thread for testing purposes.
        // This is not recommended for production code, but can be useful in tests.
        val sdkInt = Build.VERSION.SDK_INT
        if (sdkInt > 8) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        return MockWebServerProvider.get().url("/")
    }
}