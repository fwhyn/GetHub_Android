package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fwhyn.app.gethub.common.storage.saf.SafUtil
import com.fwhyn.lib.baze.common.helper.Util

object ExportExcelUtil {

    fun requestToCreateWorkBook(launcher: ActivityResultLauncher<Intent>) {
        val mimeType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        val time = Util.getDateTime()
        val fileName = "KmcData_$time.xlsx"

        SafUtil.createFile(
            launcher = launcher,
            mimeType = mimeType,
            fileName = fileName
        )
    }
}