package com.jesusvilla.openpay

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jesusvilla.openpay.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class OpenpayApplication : Application(){

    /*@Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    private val workManager by lazy {
        setupWorkManager()
    }*/

    companion object {
        const val LOCATION_WORK_TAG = "LOCATION_WORK_TAG"
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
                //startWorkManager()
    }

    /*private fun setupWorkManager() = WorkManager.getInstance(this)

    private fun startWorkManager() {
        // initialize WorkManager
        if(!WorkManager.isInitialized()) {
            WorkManager.initialize(this, workManagerConfiguration)
        }
        val locationWorker = PeriodicWorkRequestBuilder<TrackLocationWorker>(1, TimeUnit.MINUTES).build()
        workManager.enqueueUniquePeriodicWork(
            LOCATION_WORK_TAG,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            locationWorker
        )
    }*/
}