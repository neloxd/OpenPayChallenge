package com.jesusvilla.openpay.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.jesusvilla.openpay.R
import com.jesusvilla.openpay.service.CrashTrackerService
import com.jesusvilla.openpay.service.LocationService
import com.jesusvilla.openpay.service.LocationService.Companion.CHANNEL_ID
import com.jesusvilla.openpay.service.LocationService.Companion.NOTIFICATION_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltWorker
class TrackerLocationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val crashTrackerService: CrashTrackerService,
) : CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {
        Timber.i("doWork: running my work Timber")
        return try {
            val serviceIntent = Intent(applicationContext, LocationService::class.java)
            startForegroundService(applicationContext, serviceIntent)
            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.retry()
        }
    }

    companion object {
        const val TAG = "MyWork"
        private const val DEFAULT_MIN_INTERVAL = 1L

        fun oneTimeWorkRequest(): OneTimeWorkRequest {
            val constrains = Constraints.Builder()
                //.setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()

            return OneTimeWorkRequestBuilder<TrackerLocationWorker>()
                .setConstraints(constrains)
                .addTag(TAG)
                .build()
        }

        fun periodicWorkRequest(): PeriodicWorkRequest {
            val constrains = Constraints.Builder()
                //.setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .setRequiresCharging(true)
                .build()
            return PeriodicWorkRequestBuilder<TrackerLocationWorker>(
                DEFAULT_MIN_INTERVAL,
                TimeUnit.MINUTES
            ).setConstraints(constrains)
                .build()
        }
    }

}