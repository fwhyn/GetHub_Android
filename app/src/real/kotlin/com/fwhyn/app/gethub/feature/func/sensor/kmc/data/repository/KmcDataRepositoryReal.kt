package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.GetKmcDataParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.SetKmcDataParam
import javax.inject.Inject

class KmcDataRepositoryReal @Inject constructor(

) : KmcDataRepository {
    override suspend fun get(param: GetKmcDataParam): KmcData {
        TODO("Not yet implemented")
    }

    override suspend fun set(param: SetKmcDataParam, data: KmcData) {
        TODO("Not yet implemented")
    }

}