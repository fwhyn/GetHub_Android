package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model

class TemperatureData(
    val data: Float,
    val unit: String,
) {
    companion object {
        fun default(
            data: Float = 0f,
            unit: String = "°C",
        ) = TemperatureData(
            data = data,
            unit = unit,
        )
    }
}