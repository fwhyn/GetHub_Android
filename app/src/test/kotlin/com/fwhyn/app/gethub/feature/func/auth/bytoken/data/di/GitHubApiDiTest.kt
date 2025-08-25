package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.di

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider
import okhttp3.HttpUrl

//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [GitHubApiDiFake::class]
//)
class GitHubApiDiTest {

    //    @Provides
//    @GitHubApi
    fun baseUrl(): HttpUrl {
        return MockWebServerProvider.httpUrl()
    }
}