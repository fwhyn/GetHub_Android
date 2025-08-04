package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class CommitAuthorData(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
)
