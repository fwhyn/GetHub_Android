package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model

data class KmcData(
    val timeStamp: Long,
    val spoO2: SpoO2Data,
    val temperature: TemperatureData,
    val respirationRate: RespirationRateData,
) {
    companion object {
        fun default(
            timeStamp: Long = 0,
            spoO2Ui: SpoO2Data = SpoO2Data.default(),
            temperatureUi: TemperatureData = TemperatureData.default(),
            respirationRateUi: RespirationRateData = RespirationRateData.default(),
        ) = KmcData(
            timeStamp = timeStamp,
            spoO2 = spoO2Ui,
            temperature = temperatureUi,
            respirationRate = respirationRateUi,
        )
    }
}

val kmcDataFake = KmcData(
    timeStamp = 1696156800L,
    spoO2 = SpoO2Data.default(data = 98),
    temperature = TemperatureData.default(data = 36.5f),
    respirationRate = RespirationRateData.default(data = 16)
)

val kmcDataListFake = listOf(
    kmcDataFake,
    KmcData(
        timeStamp = 1696157100L,
        spoO2 = SpoO2Data.default(data = 97),
        temperature = TemperatureData.default(data = 36.6f),
        respirationRate = RespirationRateData.default(data = 18)
    ),
    KmcData(
        timeStamp = 1696157400L,
        spoO2 = SpoO2Data.default(data = 95),
        temperature = TemperatureData.default(data = 36.7f),
        respirationRate = RespirationRateData.default(data = 20)
    ),
    KmcData(
        timeStamp = 1696157700L,
        spoO2 = SpoO2Data.default(data = 96),
        temperature = TemperatureData.default(data = 36.8f),
        respirationRate = RespirationRateData.default(data = 19)
    ),
)