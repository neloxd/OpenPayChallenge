package com.jesusvilla.openpay.di

import android.content.Context
import com.jesusvilla.openpay.initializer.WorkManagerInitializer
import com.jesusvilla.openpay.initializer.CrashTrackerInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {
    fun inject(crashTrackerInitializer: CrashTrackerInitializer)
    fun inject(crashTrackerInitializer: WorkManagerInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

}