package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.TemperatureData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.TemperatureDomain

fun TemperatureData.toDomain(): TemperatureDomain {
    return TemperatureDomain(
        data = this.data,
        unit = this.unit,
    )
}