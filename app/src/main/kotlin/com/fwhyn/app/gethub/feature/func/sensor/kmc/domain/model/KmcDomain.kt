package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model

data class KmcDomain(
    val timeStamp: Long,
    val spoO2: SpoO2Domain,
    val temperature: TemperatureDomain,
    val respirationRate: RespirationRateDomain,
)