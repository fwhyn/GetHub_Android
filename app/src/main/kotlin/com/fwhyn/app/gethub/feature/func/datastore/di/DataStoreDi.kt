package com.fwhyn.app.gethub.feature.func.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.fwhyn.app.gethub.feature.func.datastore.data.local.EncryptedUserPrefsDataSource
import com.fwhyn.app.gethub.feature.func.datastore.data.model.UserPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataStoreDi {

    companion object {
        const val ENCRYPTED_USER_PREFS_DATASTORE_GETHUB = "ENCRYPTED_USER_PREFS_DATASTORE_GETHUB"
    }

    @Provides
    @Singleton
    fun encryptedUserPrefsDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPrefs> {
        return DataStoreFactory.create(
            serializer = EncryptedUserPrefsDataSource,
            produceFile = { context.dataStoreFile(ENCRYPTED_USER_PREFS_DATASTORE_GETHUB) }
        )
    }
}