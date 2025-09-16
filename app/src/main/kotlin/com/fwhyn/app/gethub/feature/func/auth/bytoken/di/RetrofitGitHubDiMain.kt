package com.fwhyn.app.gethub.feature.func.auth.bytoken.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper.RetrofitProvider
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local.AuthTokenLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
open class RetrofitGitHubDiMain {

    private var token = ""

    @Provides
    @GitHubApi
    fun retrofit(
        @GitHubApi scope: CoroutineScope,
        @GitHubApi baseUrl: HttpUrl,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        collectToken(
            scope = scope,
            authTokenLocalDataSource = authTokenLocalDataSource,
        ) {
            token = it
        }

        return retrofit(
            baseUrl = baseUrl,
            onGetToken = {
                token
            }
        )
    }

    @Provides
    @GitHubApi
    fun coroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    fun collectToken(
        scope: CoroutineScope,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
        onOmitted: (String) -> Unit
    ) {
        scope.launch {
            authTokenLocalDataSource.getFlow().collect {
                val token = it?.value ?: ""
                onOmitted(token)
            }
        }
    }

    fun retrofit(
        baseUrl: HttpUrl,
        onGetToken: () -> String,
    ): Retrofit {
        val builder = RetrofitProvider
            .getCustomBuilder(baseUrl)
            .addBearerAuth(onGetToken)

        return builder.build()
    }
}