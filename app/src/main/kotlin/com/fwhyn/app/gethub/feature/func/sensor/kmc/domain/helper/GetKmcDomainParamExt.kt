package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.helper

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.GetKmcDataParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GetKmcDomainParam

fun GetKmcDomainParam.toData(): GetKmcDataParam {
    return GetKmcDataParam()
}