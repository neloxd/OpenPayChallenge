package com.jesusvilla.openpay.service

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.jesusvilla.openpay.worker.TrackerLocationWorker
import java.util.concurrent.TimeUnit

class TaskRepository(private val appContext: Context) {

    fun runWork() {
        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueueUniqueWork(
            "sync_work",
            ExistingWorkPolicy.REPLACE,
            TrackerLocationWorker.oneTimeWorkRequest()
        )
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    fun runPeriodicWork() {
        val workManager = WorkManager.getInstance(appContext)
        /*workManager.enqueueUniquePeriodicWork(
            TrackerLocationWorker.TAG,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            TrackerLocationWorker.periodicWorkRequest()
        )*/


        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .setRequiresCharging(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<TrackerLocationWorker>()
            .setInputData(workDataOf("LogWorker.MESSAGE" to "This is an unique one time work"))
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        /*val uploadWorker = PeriodicWorkRequest.Builder(
            TrackerLocationWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        */
            //workManager.enqueue(workRequest)

        val uploadWorker1 = PeriodicWorkRequest.Builder(
            TrackerLocationWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(uploadWorker1)
    }


}