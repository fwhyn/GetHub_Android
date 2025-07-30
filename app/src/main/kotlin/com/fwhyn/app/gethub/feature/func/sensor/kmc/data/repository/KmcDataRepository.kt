package com.fwhyn.app.gethub.feature.func.sensor.kmc.data.repository

import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.GetKmcDataParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.KmcData
import com.fwhyn.app.gethub.feature.func.sensor.kmc.data.model.SetKmcDataParam
import com.fwhyn.lib.baze.common.helper.BaseGetter
import com.fwhyn.lib.baze.common.helper.BaseSetter

interface KmcDataRepository :
    BaseGetter<GetKmcDataParam, KmcData>,
    BaseSetter<SetKmcDataParam, KmcData>