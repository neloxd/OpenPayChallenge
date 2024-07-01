package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.core.network.data.processedNetworkResource
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.data.service.StoresAPI
import com.jesusvilla.profile.di.StoresQualifier
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    @StoresQualifier private val storesService: StoresAPI
) : StoresRepository {

    override suspend fun getStores(perPage: Int, page: Int): Resource<StoresResponse> {
        return processedNetworkResource(
            {
                storesService.getStores(
                    perPage = perPage,
                    page = page
                )
            },
            {
                it
            }
        )
    }
}