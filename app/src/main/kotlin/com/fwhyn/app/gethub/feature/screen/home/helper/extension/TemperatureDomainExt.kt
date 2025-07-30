package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.TemperatureDomain
import com.fwhyn.app.gethub.feature.screen.home.model.TemperatureUi

fun TemperatureDomain.toUi(): TemperatureUi {
    return TemperatureUi(
        data = this.data,
        unit = this.unit
    )
}