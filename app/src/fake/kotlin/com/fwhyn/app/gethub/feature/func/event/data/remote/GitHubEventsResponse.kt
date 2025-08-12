package com.fwhyn.app.gethub.feature.func.event.data.remote

import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsSuccessResponse.fwhyn_item20_page1
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsSuccessResponse.fwhyn_item20_page2
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsSuccessResponse.fwhyn_item20_page3
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsSuccessResponse.wycats_item20_page1
import com.fwhyn.app.gethub.feature.func.event.data.remote.GitHubEventsSuccessResponse.wycats_item20_page2
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubQueryParam
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubEventsResponse {
    private val mockData = mapOf(
        "wycats" to mapOf(
            1 to wycats_item20_page1,
            2 to wycats_item20_page2,
        ),
        "fwhyn" to mapOf(
            1 to fwhyn_item20_page1,
            2 to fwhyn_item20_page2,
            3 to fwhyn_item20_page3
        )
    )

    fun getOrNull(request: RecordedRequest): MockResponse? {
        val url = request.requestUrl
        val perPage = GitHubQueryParam.perPageParam(request)
        val events = url?.pathSegments?.getOrNull(2)
        if (perPage != 20 || events != "events") return null

        val login = url.pathSegments.getOrNull(1)
        val page = GitHubQueryParam.pageParam(request)
        val body = mockData[login]?.get(page) ?: return null

        return MockResponse()
            .setResponseCode(200)
            .setBody(body)
    }
}