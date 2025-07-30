package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.remote

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcSensorParam

class KmcSensorDataSourceReal : KmcSensorDataSource {

    override suspend fun get(param: KmcSensorParam): KmcData {
        TODO("Implement the method to fetch KmcData from the remote source")
    }
}