package com.fwhyn.app.gethub.feature.screen.home.model

data class SpoO2Ui(
    val data: Int,
    val unit: String,
) {
    companion object {
        fun default(
            data: Int = 0,
            unit: String = "%",
        ) = SpoO2Ui(
            data = data,
            unit = unit,
        )
    }
}