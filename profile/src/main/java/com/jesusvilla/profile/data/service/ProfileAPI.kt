package com.jesusvilla.profile.data.service

import com.jesusvilla.core.network.data.BaseResponse
import com.jesusvilla.base.models.BaseDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileAPI {

    @GET("person/popular")
    suspend fun getPopularProfiles(): Response<BaseResponse<List<BaseDataResponse>>>

}