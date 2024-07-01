package com.jesusvilla.openpay.initializer

import android.content.Context
import androidx.startup.Initializer
import com.jesusvilla.openpay.di.InitializerEntryPoint
import com.jesusvilla.openpay.service.CrashTrackerService
import com.jesusvilla.openpay.service.LocationService
import javax.inject.Inject

class CrashTrackerInitializer : Initializer<CrashTrackerService> {

    @Inject
    lateinit var service: CrashTrackerService

    @Inject
    lateinit var locationService: LocationService

    override fun create(context: Context): CrashTrackerService {
        InitializerEntryPoint.resolve(context).inject(this)
        service.initialize(locationService)

        return service
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }

}