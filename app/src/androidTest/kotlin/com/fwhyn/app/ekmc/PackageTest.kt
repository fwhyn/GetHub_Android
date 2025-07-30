package com.fwhyn.app.gethub

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PackageTest {

    @Test
    fun isPackageCorrect() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val packageName = "com.fwhyn.app.gethub"
        if (BuildConfig.DEBUG) {
            assertEquals("$packageName.debug", appContext.packageName)
        } else {
            assertEquals(packageName, appContext.packageName)
        }

    }
}