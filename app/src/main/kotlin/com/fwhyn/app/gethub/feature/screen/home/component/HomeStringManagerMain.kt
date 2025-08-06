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
            HomeMessageCode.NoInternetConnection -> R.string.no_internet_connection
            HomeMessageCode.DataNotFound -> R.string.data_not_found
            HomeMessageCode.ReadDataError -> R.string.read_data_error
        }

        return context.getString(id)
    }
}