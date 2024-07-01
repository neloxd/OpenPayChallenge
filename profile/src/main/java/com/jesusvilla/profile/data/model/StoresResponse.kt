package com.jesusvilla.profile.data.model

import com.google.gson.annotations.SerializedName

data class StoresResponse (
    @SerializedName("data") var data: ArrayList<Data>? = null,
    @SerializedName("meta") var meta: Meta? = null,
    @SerializedName("links") var links: Links? = null
)