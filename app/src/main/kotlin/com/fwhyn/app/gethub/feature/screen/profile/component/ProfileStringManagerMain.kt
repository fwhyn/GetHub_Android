package com.fwhyn.app.gethub.feature.screen.profile.component

import android.content.Context
import androidx.annotation.StringRes
import com.fwhyn.app.gethub.R

class ProfileStringManagerMain(
    private val context: Context,
) : ProfileStringManager {

    override fun getString(input: ProfileMessageCode): String {
        @StringRes
        val id: Int = when (input) {
            ProfileMessageCode.UnexpectedError -> R.string.unexpected_error
            ProfileMessageCode.TimeOutError -> R.string.time_out_error
            ProfileMessageCode.NetworkError -> R.string.network_error
            ProfileMessageCode.DataNotFound -> R.string.data_not_found
            ProfileMessageCode.ReadDataError -> R.string.read_data_error
            ProfileMessageCode.EmptyResult -> R.string.emmpty_result
            ProfileMessageCode.Unauthorized -> R.string.unauthorized
        }

        return context.getString(id)
    }
}