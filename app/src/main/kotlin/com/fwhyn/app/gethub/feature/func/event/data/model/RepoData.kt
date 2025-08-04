package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class RepoData(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)
