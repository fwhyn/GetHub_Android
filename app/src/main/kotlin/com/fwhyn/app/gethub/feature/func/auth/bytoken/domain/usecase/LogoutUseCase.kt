package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LogoutResult
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class LogoutUseCase : BaseRunner<Unit, LogoutResult>()