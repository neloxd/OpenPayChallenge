package com.jesusvilla.profile.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GetPopularProfileUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StoresQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StoresDynamicQualifiers

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StoresRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StoresDynamicRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GetStoresUseCaseQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GetMoreStoresUseCaseQualifier