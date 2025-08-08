package com.fwhyn.app.gethub.feature.screen.home.component

import android.content.Context
import androidx.annotation.StringRes
import com.fwhyn.app.gethub.R

class HomeStringManagerMain(
    private val context: Context,
) : HomeStringManager {

    override fun getString(input: HomeMessageCode): String {
        @StringRes
        val id: Int = when (input) {
            HomeMessageCode.UnexpectedError -> R.string.unexpected_error
            HomeMessageCode.TimeOutError -> R.string.time_out_error
            HomeMessageCode.NetworkError -> R.string.network_error
            HomeMessageCode.DataNotFound -> R.string.data_not_found
            HomeMessageCode.ReadDataError -> R.string.read_data_error
            HomeMessageCode.Unauthorized -> R.string.unauthorized
            HomeMessageCode.EmptyResult -> R.string.emmpty_result
        }

        return context.getString(id)
    }
}