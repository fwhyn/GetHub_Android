package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.success
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubUserResponse {
    fun getOrNull(request: RecordedRequest): MockResponse? {
        val url = request.requestUrl
        val urlSegments = url?.pathSegments
        val user = urlSegments?.getOrNull(0)
        if (urlSegments?.size != 1 || user != "user") return null

        return MockResponse()
            .setResponseCode(200)
            .setBody(success)
    }
}