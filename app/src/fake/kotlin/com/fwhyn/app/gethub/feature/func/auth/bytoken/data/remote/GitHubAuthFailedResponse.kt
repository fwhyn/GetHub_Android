package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

object GitHubAuthFailedResponse {

    // TODO use this token in fake mode variant
    const val TOKEN = "admin"

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