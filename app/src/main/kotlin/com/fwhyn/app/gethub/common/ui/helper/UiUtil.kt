package com.fwhyn.app.gethub.common.ui.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object UiUtil {

    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    // convert time stamp to date string
    fun convertTimeStampToDateString(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        return format.format(date)
    }

    fun convertDateStringToTimeStamp(dateString: String): Long {
        return try {
            val date = format.parse(dateString)
            date?.time?.div(1000) ?: 0

        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }
}