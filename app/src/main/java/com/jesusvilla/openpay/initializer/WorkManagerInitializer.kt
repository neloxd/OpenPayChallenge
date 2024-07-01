package com.jesusvilla.openpay.initializer

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import com.jesusvilla.openpay.di.InitializerEntryPoint
import timber.log.Timber
import javax.inject.Inject

class WorkManagerInitializer : Initializer<WorkManager>, Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun create(context: Context): WorkManager {
        Timber.i("create!!!")
        InitializerEntryPoint.resolve(context).inject(this)
        WorkManager.initialize(context, workManagerConfiguration)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(DependencyGraphInitializer::class.java)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}