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
class RetrofitGitHubDiMain {

    @Provides
    @GitHubApi
    fun retrofit(
        @GitHubApi baseUrl: HttpUrl,
        authTokenLocalDataSource: AuthTokenLocalDataSource,
    ): Retrofit {
        var token = ""
        CoroutineScope(Dispatchers.Default).launch {
            authTokenLocalDataSource.getFlow().collect {
                token = it?.value ?: ""
            }
        }
        val builder = RetrofitProvider
            .getCustomBuilder(baseUrl)
            .addBearerAuth { token }

        return builder.build()
    }
}