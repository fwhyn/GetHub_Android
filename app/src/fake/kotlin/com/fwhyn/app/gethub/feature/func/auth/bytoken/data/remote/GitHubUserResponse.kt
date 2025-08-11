package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.GitHubUserSuccessResponse.success
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class GitHubUserResponse {
    fun getOrNull(request: RecordedRequest): MockResponse? {
        if (request.path != "/user") {
            return null
        }

        return MockResponse()
            .setResponseCode(200)
            .setBody(success)
    }
}