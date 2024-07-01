package com.jesusvilla.movies.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviesRepositoryQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GetMoviesUseCaseQualifier