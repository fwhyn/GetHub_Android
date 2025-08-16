package com.fwhyn.app.gethub.common.helper.extension

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

suspend inline fun <reified T> DataStore<Preferences>.get(key: String): T? {
    return getFlow<T>(key).first()
}

inline fun <reified T> DataStore<Preferences>.getFlow(key: String): Flow<T?> {
    return data.map { prefs ->
        val value = prefs[stringPreferencesKey(key)]
        Gson().fromJson(value, T::class.java)
    }
}

suspend fun <T> DataStore<Preferences>.set(key: String, data: T) {
    val prefsKey = stringPreferencesKey(key)
    val jsonString = if (data != null) Gson().toJson(data) else null

    edit { prefs ->
        if (jsonString == null) prefs.remove(prefsKey) else prefs[prefsKey] = jsonString
    }
}

suspend fun DataStore<Preferences>.del(key: String) {
    edit { prefs ->
        prefs.remove(stringPreferencesKey(key))
    }
}