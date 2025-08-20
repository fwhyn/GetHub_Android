package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubAuthResponse {

    // TODO use this token in fake mode variant or test
    const val TOKEN_FAKE = "admin"

    const val LIMITED_TOKEN_FAKE = "limited_admin"

    val errorAuthResponse = MockResponse().setResponseCode(401)

    fun isRequiredAuthentication(request: RecordedRequest): Boolean {
        val authHeader = request.getHeader("Authorization")
        val token = getToken(request)

        return authHeader == null || token == null || token.isBlank()
    }

    fun isBadCredential(request: RecordedRequest): Boolean {
        val token = getToken(request)
        return token != TOKEN_FAKE
    }

    fun isLimited(request: RecordedRequest): Boolean {
        val token = getToken(request)
        return token == LIMITED_TOKEN_FAKE
    }

    private fun getToken(request: RecordedRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return authHeader?.removePrefix("Bearer ")
    }
}