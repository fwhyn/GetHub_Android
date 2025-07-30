package com.fwhyn.app.gethub.common.storage.saf

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import java.io.OutputStream

object SafUtil {

    fun getFileCreatorLauncher(
        activity: ComponentActivity,
        callback: ActivityResultCallback<ActivityResult>,
    ): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            callback
        )
    }

    fun createFile(launcher: ActivityResultLauncher<Intent>, mimeType: String, fileName: String) {
        val intent = getIntentForFileCreation(mimeType, fileName)
        launcher.launch(intent)
    }

    fun getIntentForFileCreation(mimeType: String, fileName: String): Intent {
        return Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
    }

    fun getGrantedPathOrNull(resultCode: Int, resultData: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) {
            resultData?.data
        } else {
            null
        }
    }

    fun saveFile(appContext: Context, uri: Uri, onWrite: (outStream: OutputStream) -> Unit) {
        appContext.contentResolver.openOutputStream(uri)?.use { outStream ->
            onWrite(outStream)
        }
    }
}

@Composable
fun getFileCreatorLauncher(
    onResult: (ActivityResult) -> Unit,
): ActivityResultLauncher<Intent> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onResult
    )
}