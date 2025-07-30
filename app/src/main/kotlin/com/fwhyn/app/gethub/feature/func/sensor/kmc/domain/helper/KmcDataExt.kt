package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain

fun KmcData.toDomain(): KmcDomain {
    return KmcDomain(
        timeStamp = this.timeStamp,
        spoO2 = this.spoO2.toDomain(),
        temperature = this.temperature.toDomain(),
        respirationRate = this.respirationRate.toDomain()
    )
}