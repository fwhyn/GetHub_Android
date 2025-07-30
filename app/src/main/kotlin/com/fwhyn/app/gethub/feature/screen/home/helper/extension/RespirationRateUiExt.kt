package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.RespirationRateDomain
import com.fwhyn.app.gethub.feature.screen.home.model.RespirationRateUi

fun RespirationRateUi.toDomain(): RespirationRateDomain {
    return RespirationRateDomain(
        data = this.data,
        unit = this.unit
    )
}