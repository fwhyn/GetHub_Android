package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote.MockWebServerProvider.Companion.TOKEN
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubAuthFailedResponse {

    val errorAuthResponse = MockResponse().setResponseCode(401)

    fun isRequiredAuthentication(request: RecordedRequest): Boolean {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.removePrefix("Bearer ")

        return authHeader == null || token == null || token.isBlank()
    }

    fun isBadCredential(request: RecordedRequest): Boolean {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.removePrefix("Bearer ")

        return token != TOKEN
    }
}