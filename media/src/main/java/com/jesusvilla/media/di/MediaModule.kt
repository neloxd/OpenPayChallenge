package com.jesusvilla.media.di

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MediaModule {

    @Provides
    @ViewModelScoped
    @FirebaseStorageInstanceQualifier
    fun provideFirebaseInstanceStorage() = FirebaseStorage.getInstance()

}