package com.jesusvilla.movies.data.repository

import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.core.network.data.Resource

interface MoviesRepository {
    @Throws(Exception::class)
    suspend fun getMovies(): Resource<List<BaseDataItemResponse>>
}