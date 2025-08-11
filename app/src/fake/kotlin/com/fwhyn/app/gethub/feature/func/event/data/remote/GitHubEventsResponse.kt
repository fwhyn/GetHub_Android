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

    fun getOrNull(request: RecordedRequest): MockResponse? {
        val users = "/users"
        val repos = "/events"
        val path = request.path
        val perPage = GitHubQueryParam.perPageParam(request)?.toIntOrNull()
        val page = GitHubQueryParam.pageParam(request)?.toIntOrNull()

        val successResponse = MockResponse().setResponseCode(200)

        return when {
            path == (users + "wycats" + repos) && perPage == 20 -> when (page) {
                1 -> successResponse.setBody(wycats_item20_page1)
                2 -> successResponse.setBody(wycats_item20_page2)
                else -> null
            }

            path == (users + "fwhyn" + repos) && perPage == 20 -> when (page) {
                1 -> successResponse.setBody(fwhyn_item20_page1)
                2 -> successResponse.setBody(fwhyn_item20_page2)
                3 -> successResponse.setBody(fwhyn_item20_page3)
                else -> null
            }

            else -> null
        }
    }
}