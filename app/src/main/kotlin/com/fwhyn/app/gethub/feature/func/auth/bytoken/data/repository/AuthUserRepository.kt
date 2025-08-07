package com.fwhyn.app.gethub.feature.func.auth.bytoken.data.repository

import com.fwhyn.app.gethub.feature.func.auth.bytoken.data.model.AuthUserData
import com.fwhyn.lib.baze.common.helper.BaseGetter

interface AuthUserRepository : BaseGetter<Unit, AuthUserData>