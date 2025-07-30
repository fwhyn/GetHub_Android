package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.common.ui.helper.UiUtil
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import com.fwhyn.app.gethub.feature.screen.home.model.KmcUi

fun KmcUi.toDomain(): KmcDomain {
    return KmcDomain(
        timeStamp = UiUtil.convertDateStringToTimeStamp(this.timeStamp),
        spoO2 = this.spoO2.toDomain(),
        temperature = this.temperature.toDomain(),
        respirationRate = this.respirationRate.toDomain()
    )
}

fun List<KmcUi>.toDomain(): List<KmcDomain> {
    return this.map {
        it.toDomain()
    }
}