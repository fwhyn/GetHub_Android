package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.SpoO2Data
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.SpoO2Domain

fun SpoO2Data.toDomain(): SpoO2Domain {
    return SpoO2Domain(
        data = this.data,
        unit = this.unit,
    )
}