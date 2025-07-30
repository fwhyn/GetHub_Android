package com.fwhyn.app.gethub.feature.screen.home.model

data class RespirationRateUi(
    val data: Int,
    val unit: String,
) {
    companion object {
        fun default(
            data: Int = 0,
            unit: String = "breaths/min",
        ) = RespirationRateUi(
            data = data,
            unit = unit,
        )
    }
}