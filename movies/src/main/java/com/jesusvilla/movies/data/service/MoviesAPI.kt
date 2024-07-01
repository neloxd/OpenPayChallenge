package com.jesusvilla.movies.data.service

import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.core.network.data.BaseResponse
import com.jesusvilla.base.models.BaseDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesAPI {

    @GET("trending/movie/day?language=en-US")
    suspend fun getMovies(): Response<BaseResponse<List<BaseDataItemResponse>>>

}