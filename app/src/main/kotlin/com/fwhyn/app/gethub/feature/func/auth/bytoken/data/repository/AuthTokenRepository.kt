package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthTokenData
import com.fwhyn.lib.baze.common.helper.BaseGetter
import com.fwhyn.lib.baze.common.helper.BaseSetter

interface AuthTokenRepository :
    BaseGetter<Unit, AuthTokenData?>,
    BaseSetter<Unit, AuthTokenData?>