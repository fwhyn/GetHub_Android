package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.TemperatureDomain
import com.fwhyn.app.gethub.feature.screen.home.model.TemperatureUi

fun TemperatureUi.toDomain(): TemperatureDomain {
    return TemperatureDomain(
        data = this.data,
        unit = this.unit
    )
}