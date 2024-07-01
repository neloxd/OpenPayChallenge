package com.jesusvilla.location.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LocationModule {

    @Provides
    @ViewModelScoped
    @FirebaseDatabaseInstanceQualifier
    fun provideFirebaseInstanceDatabase() = FirebaseDatabase.getInstance().reference

}