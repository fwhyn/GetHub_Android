package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.errorAuthResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.isBadCredential
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.isLimited
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthResponse.isRequiredAuthentication
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubFailedResponse.notFoundResponse
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersResponse
import okhttp3.HttpUrl
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

object MockWebServerProvider {

    fun httpUrl(): HttpUrl {
        return get().url("/")
    }

    fun get(): MockWebServer {
        val mockWebServer = MockWebServer()

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when {
                    isRequiredAuthentication(request) ->
                        errorAuthResponse.setBody(GitHubFailedResponse.requireAuthentication)

                    isLimited(request) ->
                        errorAuthResponse.setBody(GitHubFailedResponse.apiLimited)

                    isBadCredential(request) ->
                        errorAuthResponse.setBody(GitHubFailedResponse.badCredential)

                    else -> sequenceOf(
                        GitHubUserResponse.getOrNull(request),
                        GitHubUsersByLoginResponse.getOrNull(request),
                        GitHubEventsResponse.getOrNull(request),
                        GitHubReposResponse.getOrNull(request),
                        GitHubUsersResponse.getOrNull(request)
                    ).firstOrNull { it != null } ?: notFoundResponse
                }
            }
        }

        return mockWebServer
    }
}