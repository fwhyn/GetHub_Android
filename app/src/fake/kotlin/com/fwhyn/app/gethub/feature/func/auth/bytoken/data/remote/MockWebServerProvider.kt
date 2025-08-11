package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.errorAuthResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isBadCredential
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isRequiredAuthentication
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class MockWebServerProvider {

    companion object {
        // TODO use this token in fake mode variant
        const val TOKEN = "admin"
    }

    fun get(): MockWebServer {
        val mockWebServer = MockWebServer()

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (isRequiredAuthentication(request)) {
                    return errorAuthResponse.setBody(GitHubFailedResponse.requireAuthentication)
                }

                if (isBadCredential(request)) {
                    return errorAuthResponse.setBody(GitHubFailedResponse.badCredential)
                }

                val successResponse = MockResponse().setResponseCode(200)

                return successResponse
            }
        }

        return mockWebServer
    }
}