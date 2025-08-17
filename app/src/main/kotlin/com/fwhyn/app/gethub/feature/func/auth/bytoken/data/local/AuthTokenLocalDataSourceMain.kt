package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.local

import androidx.datastore.core.DataStore
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.app.gethub.feature.func.datastore.data.model.UserPrefs
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthTokenLocalDataSourceMain @Inject constructor(
    private val encryptedUserPrefs: DataStore<UserPrefs>
) : AuthTokenLocalDataSource {

    override suspend fun get(): AuthTokenData? {
        return getFlow().first()
    }

    override fun getFlow(): Flow<AuthTokenData?> {
        return encryptedUserPrefs.data.map { prefs ->
            try {
                val value = prefs.authToken
                Gson().fromJson(value, AuthTokenData::class.java)
            } catch (_: Throwable) {
                null
            }
        }
    }

    override suspend fun set(data: AuthTokenData?) {
        val jsonString = if (data != null) Gson().toJson(data) else null

        encryptedUserPrefs.updateData { prefs ->
            val builder = prefs.toBuilder()
            if (jsonString == null) builder.clearAuthToken() else builder.setAuthToken(jsonString)

            builder.build()
        }
    }
}