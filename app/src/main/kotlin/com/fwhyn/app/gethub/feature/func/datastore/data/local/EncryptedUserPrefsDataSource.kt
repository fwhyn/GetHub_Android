package com.fwhyn.app.gethub.feature.func.datastore.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.fwhyn.app.gethub.feature.func.datastore.data.helper.AesKeyProvider
import com.fwhyn.app.gethub.feature.func.datastore.data.model.UserPrefs
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import kotlin.random.Random

object EncryptedUserPrefsDataSource : Serializer<UserPrefs> {

    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val IV_SIZE_BYTES = 12
    private const val TAG_LENGTH_BITS = 128

    override val defaultValue: UserPrefs = UserPrefs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPrefs {
        try {
            val all = input.readBytes()
            if (all.isEmpty()) return defaultValue

            require(all.size > IV_SIZE_BYTES) { "Corrupted input: too short" }

            val iv = all.copyOfRange(0, IV_SIZE_BYTES)
            val cipherText = all.copyOfRange(IV_SIZE_BYTES, all.size)

            val secretKey = AesKeyProvider.getOrCreateKey()
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH_BITS, iv))
            val plain = cipher.doFinal(cipherText)

            return UserPrefs.parseFrom(plain)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot parse decrypted proto", e)
        } catch (e: Exception) {
            // Any crypto error or format issue -> treat as corruption so DataStore can recover if needed
            throw CorruptionException("Decryption failed", e)
        }
    }

    override suspend fun writeTo(t: UserPrefs, output: OutputStream) {
        val secretKey = AesKeyProvider.getOrCreateKey()
        val cipher = Cipher.getInstance(TRANSFORMATION)

        // 12-byte random IV for GCM
        val iv = Random.Default.nextBytes(IV_SIZE_BYTES)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH_BITS, iv))

        val plain = t.toByteArray()
        val cipherText = cipher.doFinal(plain)

        // Store IV || ciphertext
        output.write(iv)
        output.write(cipherText)
    }
}