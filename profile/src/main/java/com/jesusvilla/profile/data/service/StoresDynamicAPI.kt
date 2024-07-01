package com.jesusvilla.profile.data.service

import com.jesusvilla.profile.data.model.StoresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface StoresDynamicAPI {
    @GET
    fun fetch(@Url url: String): Response<StoresResponse>

   @GET("")
    suspend fun getStores(
       @Url url: String
        //@Query("per_page") perPage: Int,
        //@Query("page") page: Int,
    ): Response<StoresResponse>
}