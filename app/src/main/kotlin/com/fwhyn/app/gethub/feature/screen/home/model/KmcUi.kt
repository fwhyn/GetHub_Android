package com.fwhyn.app.gethub.feature.screen.home.model

data class KmcUi(
    val timeStamp: String,
    val spoO2: SpoO2Ui,
    val temperature: TemperatureUi,
    val respirationRate: RespirationRateUi,
) {
    companion object {
        fun default(
            timeStamp: String = "1967-01-01 12:00:00",
            spoO2Ui: SpoO2Ui = SpoO2Ui.default(),
            temperatureUi: TemperatureUi = TemperatureUi.default(),
            respirationRateUi: RespirationRateUi = RespirationRateUi.default(),
        ) = KmcUi(
            timeStamp = timeStamp,
            spoO2 = spoO2Ui,
            temperature = temperatureUi,
            respirationRate = respirationRateUi,
        )
    }
}

val kmcUiListFake = listOf(
    KmcUi(
        timeStamp = "2023-10-01 12:00:00",
        spoO2 = SpoO2Ui.default(data = 98),
        temperature = TemperatureUi.default(data = 36.5f),
        respirationRate = RespirationRateUi.default(data = 16)
    ),
    KmcUi(
        timeStamp = "2023-10-01 12:05:00",
        spoO2 = SpoO2Ui.default(data = 97),
        temperature = TemperatureUi.default(data = 36.6f),
        respirationRate = RespirationRateUi.default(data = 18)
    ),
    KmcUi(
        timeStamp = "2023-10-01 12:10:00",
        spoO2 = SpoO2Ui.default(data = 95),
        temperature = TemperatureUi.default(data = 36.7f),
        respirationRate = RespirationRateUi.default(data = 20)
    ),
    KmcUi(
        timeStamp = "2023-10-01 12:15:00",
        spoO2 = SpoO2Ui.default(data = 96),
        temperature = TemperatureUi.default(data = 36.8f),
        respirationRate = RespirationRateUi.default(data = 19)
    ),
)