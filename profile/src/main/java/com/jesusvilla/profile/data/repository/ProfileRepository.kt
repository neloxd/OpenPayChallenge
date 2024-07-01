package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.base.models.BaseDataResponse

interface ProfileRepository {
    @Throws(Exception::class)
    suspend fun getPopularProfile(): Resource<BaseDataResponse>
}