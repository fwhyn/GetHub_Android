package com.fwhyn.app.gethub.feature.func.user.data.remote

import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.defunkt
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.ezmobius
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.fwhyn
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.mojombo
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.pjhyett
import com.fwhyn.app.gethub.feature.func.user.data.remote.GitHubUsersByLoginSuccessResponse.wycats
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubUsersByLoginResponse {

    fun getOrNull(request: RecordedRequest): MockResponse? {
        val users = "/users/"
        val path = request.path
        val successResponse = MockResponse().setResponseCode(200)

        return when (path) {
            (users + "mojombo") -> successResponse.setBody(mojombo)
            (users + "defunkt") -> successResponse.setBody(defunkt)
            (users + "pjhyett") -> successResponse.setBody(pjhyett)
            (users + "wycats") -> successResponse.setBody(wycats)
            (users + "ezmobius") -> successResponse.setBody(ezmobius)
            (users + "fwhyn") -> successResponse.setBody(fwhyn)
            else -> null
        }
    }
}