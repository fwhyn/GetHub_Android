package com.fwhyn.app.gethub.feature.func.datastore.data.helper

import org.junit.Assert
import org.junit.Test

class AesEncryptorTest {
    val input = "test encryption"

    @Test
    fun encryptedDataShouldBeDifferentWithTheOriginalData() {
        val encryptedInput = AesEncryptor.encryptData(input.toByteArray(Charsets.UTF_8))

        Assert.assertNotEquals(input, encryptedInput.toString(Charsets.UTF_8))
    }

    @Test
    fun decryptedDataBeEqualsWithTheOriginalData() {
        val encryptedInput = AesEncryptor.encryptData(input.toByteArray(Charsets.UTF_8))
        val output = AesEncryptor.decryptData(encryptedInput)

        Assert.assertEquals(input, output.toString(Charsets.UTF_8))
    }
}