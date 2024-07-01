package com.jesusvilla.profile.domain.model

data class AnyStore(
    val title: String = "AnyProperty"
): BaseStore {
    override val isLoading: Boolean = true
}
