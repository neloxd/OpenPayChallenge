package com.jesusvilla.database.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileDBRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileDaoQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieDBRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieDaoQualifier