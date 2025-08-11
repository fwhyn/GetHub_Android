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

    fun getOrNull(request: RecordedRequest): MockResponse? {
        val users = "/users"
        val repos = "/repos"
        val path = request.path
        val perPage = GitHubQueryParam.perPageParam(request)?.toIntOrNull()
        val page = GitHubQueryParam.pageParam(request)?.toIntOrNull()

        val successResponse = MockResponse().setResponseCode(200)

        return when {
            path == (users + "wycats" + repos) && perPage == 10 -> when (page) {
                1 -> successResponse.setBody(wycats_item10_page1)
                2 -> successResponse.setBody(wycats_item10_page2)
                3 -> successResponse.setBody(wycats_item10_page3)
                4 -> successResponse.setBody(wycats_item10_page4)
                else -> null
            }

            path == (users + "fwhyn" + repos) && perPage == 10 -> when (page) {
                1 -> successResponse.setBody(fwhyn_item10_page1)
                2 -> successResponse.setBody(fwhyn_item10_page2)
                3 -> successResponse.setBody(fwhyn_item10_page3)
                else -> null
            }

            else -> null
        }
    }
}