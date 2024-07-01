package com.jesusvilla.profile.domain.useCase

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.base.models.BaseDataResponse
import com.jesusvilla.profile.data.repository.ProfileRepository
import com.jesusvilla.profile.di.ProfileRepositoryQualifier
import javax.inject.Inject

/**
 * Created by Jesús Villa on 29/06/24
 */
class GetPopularProfileUseCase @Inject constructor(
    @ProfileRepositoryQualifier private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(): Resource<BaseDataResponse> {
        return profileRepository.getPopularProfile()
    }
}