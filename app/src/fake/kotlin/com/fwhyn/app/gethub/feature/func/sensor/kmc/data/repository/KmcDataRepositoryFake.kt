package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.GetKmcDataParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.SetKmcDataParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.kmcDataFake
import javax.inject.Inject

class KmcDataRepositoryFake @Inject constructor() : KmcDataRepository {
    private var index = 0L
        get() {
            if (field == Long.MAX_VALUE) {
                field = 0 // Reset index after max value
            }
            field++

            return field
        }

    override suspend fun get(param: GetKmcDataParam): KmcData {
        return kmcDataFake.copy(timeStamp = index)
    }

    override suspend fun set(param: SetKmcDataParam, data: KmcData) {
        TODO("Not yet implemented")
    }

}