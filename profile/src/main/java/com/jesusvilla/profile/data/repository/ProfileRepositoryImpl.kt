package com.jesusvilla.profile.data.repository

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.core.network.data.processedNetworkResource
import com.jesusvilla.base.models.BaseDataResponse
import com.jesusvilla.profile.data.service.ProfileAPI
import com.jesusvilla.profile.di.ProfileQualifier
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    @ProfileQualifier private val profileAPI: ProfileAPI) : ProfileRepository {

    override suspend fun getPopularProfile(): Resource<BaseDataResponse> {
        return processedNetworkResource(
            {
                profileAPI.getPopularProfiles()
            },
            {
                it.payload!!.first()
            }
        )
    }
}