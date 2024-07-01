package com.jesusvilla.base.models

import com.google.gson.annotations.SerializedName

data class BaseDataResponse(
    @SerializedName("adult"                ) var adult              : Boolean?            = null,
    @SerializedName("gender"               ) var gender             : Int?                = null,
    @SerializedName("id"                   ) var id                 : Int?                = null,
    @SerializedName("known_for_department" ) var knownForDepartment : String?             = null,
    @SerializedName("name"                 ) var name               : String?             = null,
    @SerializedName("original_name"        ) var originalName       : String?             = null,
    @SerializedName("popularity"           ) var popularity         : Double?             = null,
    @SerializedName("profile_path"         ) var profilePath        : String?             = null,
    @SerializedName("known_for"            ) var baseDataItemResponse           : ArrayList<BaseDataItemResponse> = arrayListOf()
)

data class BaseDataItemResponse(
    @SerializedName("backdrop_path"     ) var backdropPath     : String?        = null,
    @SerializedName("id"                ) var id               : Int?           = null,
    @SerializedName("title"             ) var title            : String?        = null,
    @SerializedName("original_title"    ) var originalTitle    : String?        = null,
    @SerializedName("overview"          ) var overview         : String?        = null,
    @SerializedName("poster_path"       ) var posterPath       : String?        = null,
    @SerializedName("media_type"        ) var mediaType        : String?        = null,
    @SerializedName("adult"             ) var adult            : Boolean?       = null,
    @SerializedName("original_language" ) var originalLanguage : String?        = null,
    @SerializedName("genre_ids"         ) var genreIds         : ArrayList<Int> = arrayListOf(),
    @SerializedName("popularity"        ) var popularity       : Double?        = null,
    @SerializedName("release_date"      ) var releaseDate      : String?        = null,
    @SerializedName("video"             ) var video            : Boolean?       = null,
    @SerializedName("vote_average"      ) var voteAverage      : Double?        = null,
    @SerializedName("vote_count"        ) var voteCount        : Int?           = null
)
