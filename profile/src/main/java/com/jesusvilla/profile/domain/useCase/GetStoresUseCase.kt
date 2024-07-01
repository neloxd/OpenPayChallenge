package com.jesusvilla.profile.domain.useCase

import com.jesusvilla.core.network.data.Resource
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.data.repository.StoresRepository
import com.jesusvilla.profile.di.StoresRepositoryQualifier
import javax.inject.Inject

/**
 * Created by Jesús Villa on 15/06/24
 */
class GetStoresUseCase @Inject constructor(
    @StoresRepositoryQualifier private val storesRepository: StoresRepository,
) {
    suspend operator fun invoke(perPage: Int = 10, page: Int = 1, next: String? = null): Resource<StoresResponse> {
        return storesRepository.getStores(
            perPage = perPage,
            page = page
        )
    }
}