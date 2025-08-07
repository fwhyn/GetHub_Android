package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.helper

import com.fwhyn.app.gethub.BuildConfig
import com.fwhyn.lib.baze.retrofit.api.RetrofitBuilder
import okhttp3.HttpUrl

object RetrofitProvider {
    fun getCustomBuilder(baseUrl: HttpUrl): RetrofitBuilder {
        val builder = RetrofitBuilder(baseUrl)

        if (BuildConfig.DEBUG) {
            builder.enableLog()
        }

        return builder
    }
}