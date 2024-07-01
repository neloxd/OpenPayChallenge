package com.jesusvilla.profile.data.model

import com.google.gson.annotations.SerializedName

data class Attributes (

    @SerializedName("name" ) var name: String? = null,
    @SerializedName("code" ) var code: String? = null,
    @SerializedName("active") var active: Boolean? = null,
    @SerializedName("full_address") var fullAddress: String? = null,
    @SerializedName("coordinates") var coordinates: Coordinates? = null,
    @SerializedName("created_at") var createdAt: String? = null

)