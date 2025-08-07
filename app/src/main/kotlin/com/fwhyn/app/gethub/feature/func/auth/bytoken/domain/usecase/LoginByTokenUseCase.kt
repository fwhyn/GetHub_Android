package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import com.fwhyn.lib.baze.common.helper.BaseRunner

abstract class LoginByTokenUseCase : BaseRunner<LoginByTokenParam, LoginByTokenResult>()