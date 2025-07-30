package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.GetKmcDomainParam
import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.KmcDomain
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class GetKmcListUseCase : BaseRunner<GetKmcDomainParam, List<KmcDomain>>()