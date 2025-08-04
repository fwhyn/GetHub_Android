package com.fwhyn.app.gethub.feature.func.event.data.model

import com.google.gson.annotations.SerializedName

data class PayloadData(
    @SerializedName("repository_id") val repositoryId: Long,
    @SerializedName("push_id") val pushId: Long,
    @SerializedName("size") val size: Int,
    @SerializedName("distinct_size") val distinctSize: Int,
    @SerializedName("ref") val ref: String,
    @SerializedName("head") val head: String,
    @SerializedName("before") val before: String,
    @SerializedName("commits") val commits: List<CommitData>,
)
