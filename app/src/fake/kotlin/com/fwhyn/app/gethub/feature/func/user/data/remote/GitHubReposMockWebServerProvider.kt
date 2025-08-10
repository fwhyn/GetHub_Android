package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.errorAuthResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isBadCredential
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubAuthFailedResponse.isRequiredAuthentication
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubJsonFailedResponse
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubJsonFailedResponse.notFoundResponse
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page1
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page2
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page3
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page1
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page2
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page3
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

                val users = "/users/"
                val path = request.path
                val perPage = GitHubQueryParam.perPageParam(request)?.toIntOrNull()
                val page = GitHubQueryParam.pageParam(request)?.toIntOrNull()

                val successResponse = MockResponse().setResponseCode(200)

                return when {
                    path == (users + "swycats") && perPage == 10 -> when (page) {
                        1 -> successResponse.setBody(wycats_item10_page1)
                        2 -> successResponse.setBody(wycats_item10_page2)
                        3 -> successResponse.setBody(wycats_item10_page3)
                        else -> notFoundResponse
                    }

                    path == (users + "fwhyn") && perPage == 10 -> when (page) {
                        1 -> successResponse.setBody(fwhyn_item10_page1)
                        2 -> successResponse.setBody(fwhyn_item10_page2)
                        3 -> successResponse.setBody(fwhyn_item10_page3)
                        else -> notFoundResponse
                    }

                    else -> notFoundResponse
                }
            }
        }

        return mockWebServer
    }
}