package com.jesusvilla.profile.data.model

import com.google.gson.annotations.SerializedName

data class Pagination (

    @SerializedName("current_page" ) var currentPage : Int? = null,
    @SerializedName("total"        ) var total       : Int? = null,
    @SerializedName("per_page"     ) var perPage     : Int? = null

)