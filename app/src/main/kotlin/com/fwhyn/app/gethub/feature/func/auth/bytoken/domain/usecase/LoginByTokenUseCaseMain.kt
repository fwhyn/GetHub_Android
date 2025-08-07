package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import javax.inject.Inject

class LoginByTokenUseCaseMain @Inject constructor(
    private val authUserRepository: AuthUserRepository,
    private val authTokenRepository: AuthTokenRepository,
) : LoginByTokenUseCase() {

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

    override suspend fun onRunning(
        param: LoginByTokenParam,
        result: suspend (LoginByTokenResult) -> Unit,
    ) {
        param.token?.let { authTokenRepository.set(Unit, AuthTokenDomain(value = it).toData()) }

        val data = authUserRepository.get(Unit)
        val output = LoginByTokenResult(user = data.toDomain())

        result(output)
    }
}