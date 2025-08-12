package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page1
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page2
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.fwhyn_item10_page3
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page1
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page2
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page3
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubReposSuccessResponse.wycats_item10_page4
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubReposResponse {

    private val mockData = mapOf(
        "wycats" to mapOf(
            1 to wycats_item10_page1,
            2 to wycats_item10_page2,
            3 to wycats_item10_page3,
            4 to wycats_item10_page4
        ),
        "fwhyn" to mapOf(
            1 to fwhyn_item10_page1,
            2 to fwhyn_item10_page2,
            3 to fwhyn_item10_page3
        )
    )

    fun getOrNull(request: RecordedRequest): MockResponse? {
        val url = request.requestUrl
        val perPage = GitHubQueryParam.perPageParam(request)
        val repos = url?.pathSegments?.getOrNull(2)
        if (perPage != 10 || repos != "repos") return null

        val login = url.pathSegments.getOrNull(1)
        val page = GitHubQueryParam.pageParam(request)
        val body = mockData[login]?.get(page) ?: return null

        return MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }
}