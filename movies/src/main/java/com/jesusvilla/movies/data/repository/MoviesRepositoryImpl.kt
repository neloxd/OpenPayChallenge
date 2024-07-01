package com.jesusvilla.movies.data.repository

import com.jesusvilla.base.models.BaseDataItemResponse
import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.core.network.data.processedNetworkResource
import com.jesusvilla.movies.data.service.MoviesAPI
import com.jesusvilla.movies.di.MoviesQualifier
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    @MoviesQualifier private val movieAPI: MoviesAPI
) : MoviesRepository {

    override suspend fun getMovies(): Resource<List<BaseDataItemResponse>> {
        return processedNetworkResource(
            {
                movieAPI.getMovies()
            },
            {
                it.payload!!
            }
        )
    }
}