package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.core.network.data.processedNetworkResource
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.data.service.StoresDynamicAPI
import com.jesusvilla.profile.di.StoresDynamicQualifiers
import javax.inject.Inject

class StoresDynamicRepositoryImpl @Inject constructor(
    @StoresDynamicQualifiers private val baseDynamicAPI: StoresDynamicAPI
) : StoresDynamicRepository {

    override suspend fun getStores(url: String): Resource<StoresResponse> {
        return processedNetworkResource(
            {
                //baseDynamicAPI.fetch(url)
                baseDynamicAPI.getStores(url)
            },
            {
                it
            }
        )
    }
}