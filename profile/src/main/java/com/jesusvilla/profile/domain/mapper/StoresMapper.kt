package com.jesusvilla.profile.domain.mapper

import com.jesusvilla.profile.data.model.Data
import com.jesusvilla.profile.data.model.StoresResponse
import com.jesusvilla.profile.data.repository.DEFAULT_PER_PAGE
import com.jesusvilla.profile.data.repository.DEFAULT_TOTAL
import com.jesusvilla.profile.domain.model.DataStore
import com.jesusvilla.profile.domain.model.StoreUI

fun StoresResponse.toDataStore(): DataStore {
    return DataStore(
        totalStores = this.meta?.pagination?.total?: DEFAULT_TOTAL,
        perPage = this.meta?.pagination?.perPage?: DEFAULT_PER_PAGE,
        next = this.links?.next,
        stores = data?.toStoreUIList() ?: listOf()
    )
}

fun List<Data>.toStoreUIList() = this.map { it.toStoreUI() }

fun Data.toStoreUI(): StoreUI {
        return StoreUI(
            id = id.orEmpty(),
            code = attributes?.code?: "CODE_NULL",
            name = attributes?.name?: "NAME_EMPTY",
            fullAddress = attributes?.fullAddress?: "FULL_ADDRESS_EMPTY"
        )
}