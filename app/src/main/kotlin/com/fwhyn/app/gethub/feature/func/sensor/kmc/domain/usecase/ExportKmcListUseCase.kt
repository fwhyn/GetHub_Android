package com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.usecase

import com.fwhyn.app.gethub.feature.func.sensor.kmc.domain.model.ExportKmcListParam
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class ExportKmcListUseCase : BaseRunner<ExportKmcListParam, Unit>()