package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.profile.data.model.StoresResponse

interface StoresRepository {
    @Throws(Exception::class)
    suspend fun getStores(
        perPage: Int = DEFAULT_PER_PAGE,
        page: Int = DEFAULT_PAGE,
    ): Resource<StoresResponse>
}

const val DEFAULT_TOTAL = 10
const val DEFAULT_PER_PAGE = 10
const val DEFAULT_PAGE = 1