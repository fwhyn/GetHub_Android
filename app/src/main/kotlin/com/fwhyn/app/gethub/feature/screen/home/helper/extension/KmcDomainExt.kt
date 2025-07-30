package com.fwhyn.app.gethub.feature.screen.home.helper.extension

import com.fwhyn.app.gethub.common.ui.helper.UiUtil
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import com.fwhyn.app.gethub.feature.screen.home.model.KmcUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun KmcDomain.toUi(): KmcUi {
    return KmcUi(
        timeStamp = UiUtil.convertTimeStampToDateString(this.timeStamp),
        spoO2 = this.spoO2.toUi(),
        temperature = this.temperature.toUi(),
        respirationRate = this.respirationRate.toUi()
    )
}

fun List<KmcDomain>.toUi(): List<KmcUi> {
    return this.map { it.toUi() }
}

fun Flow<List<KmcDomain>>.toUi(): Flow<List<KmcUi>> {
    return this.map { kmcList -> kmcList.toUi() }
}