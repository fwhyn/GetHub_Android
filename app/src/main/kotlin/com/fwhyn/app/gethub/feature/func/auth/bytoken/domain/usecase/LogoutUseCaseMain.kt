package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LogoutResult
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
        authTokenRepository.set(Unit, AuthTokenDomain.default().toData())
        result(LogoutResult.default())
    }
}