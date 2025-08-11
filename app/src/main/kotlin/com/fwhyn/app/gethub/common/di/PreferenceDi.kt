package com.fwhyn.app.gethub.common.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PreferenceDi {

    @Qualifier
    annotation class EncryptedPrefs

    companion object {
        const val ENCRYPTED_PREFS_NAME = "ENCRYPTED_PREFS_GETHUB"
    }

    // TODO fix deprecated API
    @Provides
    @Singleton
    @EncryptedPrefs
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(ENCRYPTED_PREFS_NAME, Context.MODE_PRIVATE)
    }
}