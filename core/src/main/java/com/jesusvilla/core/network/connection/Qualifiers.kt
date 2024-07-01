package com.jesusvilla.core.network.connection

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BuilderQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticationQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseURL

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitApi

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitDynamic

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GsonInjection

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BodyInterceptor