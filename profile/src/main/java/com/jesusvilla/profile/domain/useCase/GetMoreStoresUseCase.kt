package com.jesusvilla.profile.domain.useCase

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.data.repository.StoresDynamicRepository
import com.jesusvilla.profile.di.StoresDynamicRepositoryQualifier
import javax.inject.Inject

/**
 * Created by Jesús Villa on 15/06/24
 */
class GetMoreStoresUseCase @Inject constructor(
    @StoresDynamicRepositoryQualifier private val storesDynamicRepository: StoresDynamicRepository,
) {
    suspend operator fun invoke(next: String): Resource<StoresResponse> {
        return storesDynamicRepository.getStores(next)
    }
}