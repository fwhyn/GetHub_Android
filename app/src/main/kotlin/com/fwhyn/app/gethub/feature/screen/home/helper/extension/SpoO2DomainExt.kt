package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.SpoO2Domain
import com.fwhyn.app.gethub.feature.screen.home.model.SpoO2Ui

fun SpoO2Domain.toUi(): SpoO2Ui {
    return SpoO2Ui(
        data = this.data,
        unit = this.unit
    )
}