package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since0
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since30
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersSuccessResponse.success_item20_since70
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubUsersResponse {
    fun getOrNull(request: RecordedRequest): MockResponse? {
        val url = request.requestUrl
        val urlSegments = url?.pathSegments
        val users = urlSegments?.getOrNull(0)
        val perPage = GitHubQueryParam.perPageParam(request)
        if (urlSegments?.size != 1 || users != "users" || perPage != 20) return null

        val since = GitHubQueryParam.sinceParam(request)
        val successResponse = MockResponse().setResponseCode(200)

        return when (since) {
            0 -> successResponse.setBody(success_item20_since0)
            30 -> successResponse.setBody(success_item20_since30)
            70 -> successResponse.setBody(success_item20_since70)
            else -> successResponse.setBody("[]")
        }
    }
}