package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model

import android.net.Uri

data class ExportKmcListParam(
    val kmcList: List<KmcDomain>,
    val dstPath: Uri,
)