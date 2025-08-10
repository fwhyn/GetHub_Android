package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.errorAuthResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isBadCredential
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isRequiredAuthentication
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubJsonFailedResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersJsonSuccessResponse.success_item20_since0
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersJsonSuccessResponse.success_item20_since30
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersJsonSuccessResponse.success_item20_since70
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class GitHubReposMockWebServerProvider {
    fun get(): MockWebServer {
        val mockWebServer = MockWebServer()

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (isRequiredAuthentication(request)) {
                    return errorAuthResponse.setBody(GitHubJsonFailedResponse.requireAuthentication)
                }

                if (isBadCredential(request)) {
                    return errorAuthResponse.setBody(GitHubJsonFailedResponse.badCredential)
                }

                val users = "/users"
                val path = request.path
                val perPage = GitHubQueryParam.perPageParam(request)
                val since = GitHubQueryParam.sinceParam(request)

                val successResponse = MockResponse().setResponseCode(200)

                return when {
                    perPage == null || since == null -> successResponse.setBody(success_item20_since0)
                    perPage == "20" && since == "0" -> successResponse.setBody(success_item20_since0)
                    perPage == "20" && since == "30" -> successResponse.setBody(success_item20_since30)
                    perPage == "20" && since == "70" -> successResponse.setBody(success_item20_since70)
                    else -> successResponse.setBody("[]")
                }
            }
        }

        return mockWebServer
    }
}