package com.jesusvilla.profile.domain.model

interface BaseStore {
    val isLoading: Boolean
        get() = false
}