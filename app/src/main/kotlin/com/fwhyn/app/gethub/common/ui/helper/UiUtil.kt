package com.fwhyn.app.gethub.common.ui.helper

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object UiUtil {

    fun formatToUserTimeZone(isoUtc: String): String {
        val zonedDateTime = ZonedDateTime.parse(isoUtc) // Parses UTC
        val userZoneDateTime = zonedDateTime.withZoneSameInstant(java.time.ZoneId.systemDefault())

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z", Locale.getDefault())
        return userZoneDateTime.format(formatter)
    }
}