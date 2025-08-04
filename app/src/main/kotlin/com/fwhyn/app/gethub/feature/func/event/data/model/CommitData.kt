package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class CommitData(
    @SerializedName("sha") val sha: String,
    @SerializedName("author") val author: CommitAuthorData,
    @SerializedName("message") val message: String,
    @SerializedName("distinct") val distinct: Boolean,
    @SerializedName("url") val url: String,
)
