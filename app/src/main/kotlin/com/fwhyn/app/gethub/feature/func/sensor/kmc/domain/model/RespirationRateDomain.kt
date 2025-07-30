package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model

data class RespirationRateDomain(
    val data: Int,
    val unit: String,
) {
    companion object {
        fun default(
            data: Int = 0,
            unit: String = "breaths/min",
        ) = RespirationRateDomain(
            data = data,
            unit = unit,
        )
    }
}