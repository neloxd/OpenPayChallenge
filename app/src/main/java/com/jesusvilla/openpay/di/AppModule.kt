package com.jesusvilla.openpay.di

import android.content.Context
import com.jesusvilla.openpay.service.CrashTrackerService
import com.jesusvilla.openpay.service.LocationService
import com.jesusvilla.openpay.service.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCrashTracker() = CrashTrackerService()

    @Provides
    @Singleton
    fun provideTaskRepository(@ApplicationContext context: Context) = TaskRepository(context)

    @Provides
    @Singleton
    fun provideLocationService() = LocationService()
}