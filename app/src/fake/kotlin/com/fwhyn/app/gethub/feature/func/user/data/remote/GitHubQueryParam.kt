package com.fwhyn.app.gethub.feature.func.user.data.remote

import okhttp3.mockwebserver.RecordedRequest

object GitHubQueryParam {

    fun perPageParam(request: RecordedRequest): String? {
        return request.requestUrl?.queryParameter("per_page")
    }

    fun pageParam(request: RecordedRequest): String? {
        return request.requestUrl?.queryParameter("page")
    }

    fun sinceParam(request: RecordedRequest): String? {
        return request.requestUrl?.queryParameter("since")
    }
}