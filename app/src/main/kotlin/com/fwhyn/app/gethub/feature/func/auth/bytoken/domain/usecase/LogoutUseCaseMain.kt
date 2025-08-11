package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.common.helper.StatusExt
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LogoutResult
import com.fwhyn.lib.baze.common.model.Exzeption
import javax.inject.Inject

class LogoutUseCaseMain @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
) : LogoutUseCase() {

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

    override suspend fun onRunning(
        param: Unit,
        result: suspend (LogoutResult) -> Unit,
    ) {
        try {
            authTokenRepository.set(Unit, null)
            result(LogoutResult.default())
        } catch (_: Exception) {
            throw Exzeption(StatusExt.LogoutError)
        }
    }
}