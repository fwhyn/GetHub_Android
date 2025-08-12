package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since0
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since30
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since70
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubUsersResponse {
    fun getOrNull(request: RecordedRequest): MockResponse? {
        if (request.path?.startsWith("/users") != true) {
            return null
        }

        val perPage = GitHubQueryParam.perPageParam(request)
        val since = GitHubQueryParam.sinceParam(request)

        val successResponse = MockResponse().setResponseCode(200)

        return when {
            perPage == null || since == null -> successResponse.setBody(success_item20_since0)
            perPage == 20 -> when (since) {
                0 -> successResponse.setBody(success_item20_since0)
                30 -> successResponse.setBody(success_item20_since30)
                70 -> successResponse.setBody(success_item20_since70)
                else -> successResponse.setBody("[]")
            }

            else -> successResponse.setBody("[]")
        }
    }
}