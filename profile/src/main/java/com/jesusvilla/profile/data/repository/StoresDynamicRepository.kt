package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.profile.data.model.StoresResponse

interface StoresDynamicRepository {
    @Throws(Exception::class)
    suspend fun getStores(url: String): Resource<StoresResponse>
}