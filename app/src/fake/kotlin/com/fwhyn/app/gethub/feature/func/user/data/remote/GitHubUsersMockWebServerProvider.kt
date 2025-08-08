package com.fwhyn.app.gethub.feature.func.user.data.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

class GitHubUsersMockWebServerProvider {

    companion object {
        const val DISCLAIMER = "Usage subject to terms: https://openexchangerates.org/terms"
        const val LICENSE = "https://openexchangerates.org/license"
        const val TIMESTAMP = 1748768449L
        const val BASE_RATE = "USD"

        const val DUMMY_API_KEY = "asdf8aw30nk"

        const val IS_ERROR = true

        const val MISSING_API_STATUS = 403
        const val MISSING_API_MSG = "missing_app_id"

        const val INVALID_API_STATUS = 401
        const val INVALID_APP_MSG = "invalid_app_id"

        const val METHOD_NOT_ALLOWED = "Method Not Allowed"
    }

    private val successResponse = """
        {
            "disclaimer": "$DISCLAIMER",
            "license": "$LICENSE",
            "timestamp": $TIMESTAMP,
            "base": "$BASE_RATE",
            "rates": {
                "EUR": 0.88124,
                "XDR": 0.754425,
                "XOF": 578.055413,
                "XPD": 0.00103407,
                "XPF": 105.15988,
                "XPT": 0.00095857,
                "YER": 243.662495,
                "ZAR": 17.99399,
                "ZMW": 26.609612,
                "ZWL": 322
            }
        }
        """.trimIndent()

    private val missingKeyResponse = """
        {
            "error": $IS_ERROR,
            "status": $MISSING_API_STATUS,
            "message": "$MISSING_API_MSG",
            "description": "No App ID provided. Please sign up at https://openexchangerates.org/signup, or contact support@openexchangerates.org."
        }
    """.trimIndent()

    private val invalidKeyResponse = """
        {
            "error": true,
            "status": $INVALID_API_STATUS,
            "message": "$INVALID_APP_MSG",
            "description": "Invalid App ID provided. Please sign up at https://openexchangerates.org/signup, or contact support@openexchangerates.org."
        }
    """.trimIndent()

    private val notAllowedResponse = """
        $METHOD_NOT_ALLOWED
    """.trimIndent()

    fun get(): MockWebServer {
        val mockWebServer = MockWebServer()

        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {

                val apiKeyQueryParam = request.requestUrl?.queryParameter("app_id")

                if (apiKeyQueryParam == null) {
                    return MockResponse()
                        .setResponseCode(401)
                        .setBody(missingKeyResponse)
                }

                // Check API key
                val authorized = (apiKeyQueryParam == DUMMY_API_KEY)
                if (!authorized) {
                    return MockResponse()
                        .setResponseCode(401)
                        .setBody(invalidKeyResponse)
                }

                // Simulate not allowed method
                if (request.path?.startsWith("/latest.json") != true) {
                    return MockResponse().setResponseCode(405).setBody(notAllowedResponse)
                }

                // Successful response
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(successResponse)
            }
        }

        return mockWebServer
    }
}