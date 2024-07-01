package com.jesusvilla.core.network.connection

import com.jesusvilla.core.network.data.Session
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * @author Jesus Villa
 *
 */
class DynamicUrlInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val httpUrl =  Session.nextUrl.toHttpUrl()
        Timber.tag("DynamicUrlInterceptor").i("httpUrl:$httpUrl")
        val newUrl = original.url.newBuilder()
            .scheme(httpUrl.scheme)
            .host(httpUrl.host)
            .port(httpUrl.port)
            .build()
            original = original.newBuilder()
                .url(newUrl)
                .build()
        return chain.proceed(original)
    }
}