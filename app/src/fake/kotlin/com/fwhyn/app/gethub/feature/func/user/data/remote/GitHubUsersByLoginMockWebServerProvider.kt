package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.errorAuthResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isBadCredential
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isRequiredAuthentication
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubFailedResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubFailedResponse.notFoundResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.defunkt
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.ezmobius
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.fwhyn
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.mojombo
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.pjhyett
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.wycats
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class GitHubUsersByLoginMockWebServerProvider {
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

                val users = "/users/"
                val path = request.path
                val successResponse = MockResponse().setResponseCode(200)

                return when (path) {
                    (users + "mojombo") -> successResponse.setBody(mojombo)
                    (users + "defunkt") -> successResponse.setBody(defunkt)
                    (users + "pjhyett") -> successResponse.setBody(pjhyett)
                    (users + "wycats") -> successResponse.setBody(wycats)
                    (users + "ezmobius") -> successResponse.setBody(ezmobius)
                    (users + "fwhyn") -> successResponse.setBody(fwhyn)
                    else -> notFoundResponse
                }
            }
        }

        return mockWebServer
    }
}