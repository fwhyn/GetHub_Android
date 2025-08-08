package com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.usecase

import com.fwhyn.app.gethub.common.helper.Constant.TIMEOUT_MILLIS
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthTokenRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository.AuthUserRepository
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toData
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.helper.toDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthTokenDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.AuthUserDomain
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenParam
import com.fwhyn.app.gethub.feature.func.auth.bytoken.domain.model.LoginByTokenResult
import com.fwhyn.lib.baze.common.model.Exzeption
import com.fwhyn.lib.baze.common.model.Status
import javax.inject.Inject

class LoginByTokenUseCaseMain @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val authUserRepository: AuthUserRepository,
) : LoginByTokenUseCase() {

    init {
        setTimeOutMillis(TIMEOUT_MILLIS)
    }

    override suspend fun onRunning(
        param: LoginByTokenParam,
        result: suspend (LoginByTokenResult) -> Unit,
    ) {
        val token = param.token
        val output: LoginByTokenResult

        if (token?.isBlank() == true) {
            val validatedUserDomain = getUserFromNewValidatedToken(token)
            output = LoginByTokenResult(user = validatedUserDomain)
        } else {
            val userDomain = getUserFromExistingToken()
            output = LoginByTokenResult(user = userDomain)
        }

        result(output)
    }

    private suspend fun getUserFromNewValidatedToken(token: String): AuthUserDomain {
        initializeAuthToken(token)
        return validateAuthToken(token)
    }

    private suspend fun initializeAuthToken(token: String) {
        val unvalidatedTokenData = AuthTokenDomain.default(value = token).toData()
        authTokenRepository.set(Unit, unvalidatedTokenData)
    }

    private suspend fun validateAuthToken(token: String): AuthUserDomain {
        val validatedUserDomain = authUserRepository.get(Unit).toDomain()
        val tokenData = AuthTokenDomain(value = token, validatedUser = validatedUserDomain).toData()
        authTokenRepository.set(Unit, tokenData)

        return validatedUserDomain
    }

    private suspend fun getUserFromExistingToken(): AuthUserDomain {
        val authTokenDomain = authTokenRepository.get(Unit).toDomain()
        val userDomain = authTokenDomain.validatedUser ?: throw Exzeption(Status.NotFound)

        return userDomain
    }
}