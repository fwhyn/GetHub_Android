package com.fwhyn.app.gethub

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.test.runner.AndroidJUnitRunner
import com.fwhyn.lib.baze.common.data.helper.extension.getDebugTag
import dagger.hilt.android.testing.HiltTestApplication

class MyTestRunner : AndroidJUnitRunner() {

    private val debugTag = MyTestRunner::class.java.getDebugTag()

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        Log.d(debugTag, "cl: $cl, name: $name, context: $context")

        return if (name?.contains("WithHiltTest") == true) {
            super.newApplication(cl, HiltTestApplication::class.java.name, context)
        } else {
            super.newApplication(cl, name, context)
        }
    }
}
