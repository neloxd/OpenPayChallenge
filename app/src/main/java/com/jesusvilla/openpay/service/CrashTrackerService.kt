package com.jesusvilla.openpay.service

import android.util.Log

class CrashTrackerService {

    var locationService: LocationService? = null
    var isInitialized: Boolean = false
        private set

    fun initialize(locationService: LocationService) {
        isInitialized = true
        this.locationService = locationService
    }

    fun log(tag: String?, data: String) {
        Log.i(tag, data)
    }
}