package com.fwhyn.app.gethub.feature.func.datastore.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.fwhyn.app.gethub.feature.func.datastore.data.helper.AesEncryptor
import com.fwhyn.app.gethub.feature.func.datastore.data.model.UserPrefs
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object EncryptedUserPrefsDataSource : Serializer<UserPrefs> {

    override val defaultValue: UserPrefs = UserPrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPrefs {
        try {
            val data = input.readBytes()
            if (data.isEmpty()) return defaultValue
            val plain = AesEncryptor.decryptData(data)

            return UserPrefs.parseFrom(plain)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot parse decrypted proto", e)
        } catch (e: Exception) {
            // Any crypto error or format issue -> treat as corruption so DataStore can recover if needed
            throw CorruptionException("Decryption failed", e)
        }
    }

    override suspend fun writeTo(t: UserPrefs, output: OutputStream) {
        val cipherText = AesEncryptor.encryptData(t.toByteArray())
        output.write(cipherText)
    }
}