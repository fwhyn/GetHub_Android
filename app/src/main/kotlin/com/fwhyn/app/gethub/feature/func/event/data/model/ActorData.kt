package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class ActorData(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val login: String,
    @SerializedName("display_login") val displayLogin: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    @SerializedName("url") val url: String,
    @SerializedName("avatar_url") val avatarUrl: String,
)
