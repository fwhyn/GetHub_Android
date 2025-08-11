package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import android.content.SharedPreferences
import com.fwhyn.app.gethub.common.di.PreferenceDi
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.lib.baze.common.helper.extension.get
import com.fwhyn.lib.baze.common.helper.extension.put
import javax.inject.Inject

class AuthTokenLocalDataSourceMain @Inject constructor(
    @PreferenceDi.EncryptedPrefs private val encryptedPreferences: SharedPreferences,
) : AuthTokenLocalDataSource {
    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
    }

    @get:Synchronized
    @set:Synchronized
    override var token: AuthTokenData?
        get() {
            return encryptedPreferences.get<AuthTokenData>(TOKEN_KEY)
        }
        set(value) {
            encryptedPreferences.put(TOKEN_KEY, value)
        }
}