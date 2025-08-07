package com.fwhyn.app.gethub.feature.screen.login.component

import android.content.Context
import androidx.annotation.StringRes
import com.fwhyn.app.gethub.R

class LoginStringManagerMain(
    private val context: Context,
) : LoginStringManager {

    override fun getString(input: LoginMessageCode): String {
        @StringRes
        val id: Int = when (input) {
            LoginMessageCode.UnexpectedError -> R.string.unexpected_error
            LoginMessageCode.LoginError -> R.string.login_error
            LoginMessageCode.Unauthorized -> R.string.unauthorized
            LoginMessageCode.TimeOutError -> R.string.time_out_error
            LoginMessageCode.NetworkError -> R.string.network_error
        }

        return context.getString(id)
    }
}