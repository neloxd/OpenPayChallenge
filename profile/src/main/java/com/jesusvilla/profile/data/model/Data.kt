package com.jesusvilla.profile.data.model

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("id") var id: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("attributes") var attributes: Attributes? = null,
    @SerializedName("links") var links: Links? = null,
    @SerializedName("relationships" ) var relationships: Relationships? = null

)