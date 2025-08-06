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
            LoginMessageCode.LoginError -> R.string.login_error
            LoginMessageCode.LoginSuccess -> R.string.login_success
        }

        return context.getString(id)
    }
}