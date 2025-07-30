package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.SpoO2Domain
import com.fwhyn.app.gethub.feature.screen.home.model.SpoO2Ui

fun SpoO2Ui.toDomain(): SpoO2Domain {
    return SpoO2Domain(
        data = this.data,
        unit = this.unit
    )
}