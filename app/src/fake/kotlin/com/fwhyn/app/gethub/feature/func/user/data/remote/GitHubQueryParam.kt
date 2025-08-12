package com.fwhyn.app.gethub.feature.func.user.data.remote

import okhttp3.mockwebserver.RecordedRequest

object GitHubQueryParam {

    fun perPageParam(request: RecordedRequest): Int? {
        return request.requestUrl?.queryParameter("per_page")?.toIntOrNull()
    }

    fun pageParam(request: RecordedRequest): Int? {
        return request.requestUrl?.queryParameter("page")?.toIntOrNull()
    }

    fun sinceParam(request: RecordedRequest): Int? {
        return request.requestUrl?.queryParameter("since")?.toIntOrNull()
    }
}