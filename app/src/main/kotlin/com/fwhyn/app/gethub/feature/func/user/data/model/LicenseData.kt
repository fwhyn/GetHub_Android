package com.fwhyn.app.gethub.feature.func.user.data.model

import com.google.gson.annotations.SerializedName

data class LicenseData(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("spdx_id") val spdxId: String,
    @SerializedName("url") val url: String?,
    @SerializedName("node_id") val nodeId: String,
)