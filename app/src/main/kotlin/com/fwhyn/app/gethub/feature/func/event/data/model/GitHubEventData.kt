package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class GitHubEventData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("actor") val actor: ActorData,
    @SerializedName("repo") val repo: RepoData,
    @SerializedName("public") val isPublic: Boolean,
    @SerializedName("created_at") val createdAt: String,
)
