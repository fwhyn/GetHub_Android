package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.remote

import okhttp3.mockwebserver.MockResponse

object GitHubJsonFailedResponse {
    val badCredential = """
        {
            "message": "Bad credentials",
            "documentation_url": "https://docs.github.com/rest",
            "status": "401"
        }
    """.trimIndent()

    val requireAuthentication = """
        {
            "message": "Requires authentication",
            "documentation_url": "https://docs.github.com/rest/users/users#get-the-authenticated-user",
            "status": "401"
        }
    """.trimIndent()

    val notFound = """
        {
            "message": "Not Found",
            "documentation_url": "https://docs.github.com/rest",
            "status": "404"
        }
    """.trimIndent()

    val paginationLimited = """
        {
          "message": "In order to keep the API fast for everyone, pagination is limited for this resource.",
          "documentation_url": "https://docs.github.com/v3/#pagination",
          "status": "422"
        }
    """.trimIndent()

    val apiLimited = """
        {
          "message": "API rate limit exceeded for 110.138.84.94. (But here's the good news: Authenticated requests get a higher rate limit. Check out the documentation for more details.)",
          "documentation_url": "https://docs.github.com/rest/overview/resources-in-the-rest-api#rate-limiting"
        }
    """.trimIndent()

    val notFoundResponse = MockResponse().setResponseCode(405).setBody(notFound)
}