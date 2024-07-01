package com.jesusvilla.profile.data.model

import com.google.gson.annotations.SerializedName

data class Links (
    @SerializedName("prev") var prev: String? = null,
    @SerializedName("next") var next: String? = null,
    @SerializedName("first") var first: String? = null,
    @SerializedName("last") var last: String? = null,
    @SerializedName("self") var self: String? = null

)