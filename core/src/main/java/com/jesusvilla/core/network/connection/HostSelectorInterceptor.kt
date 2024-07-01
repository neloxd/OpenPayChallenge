package com.jesusvilla.core.network.connection
import com.jesusvilla.core.network.data.Session
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.URISyntaxException


class HostSelectorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val host = Session.nextUrl.toHttpUrl()
        var request = chain.request()
        var newUrl: HttpUrl? = null
        try {
            newUrl = request.url.newBuilder()
                .scheme(host.scheme)
                .host(host.toUrl().toURI().host)
                .build()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        checkNotNull(newUrl)
        request = request.newBuilder()
            .url(host)
            .build()

        val newRequest = host.let {
            val newUrl = chain.request().url.newBuilder()
                .scheme(it.scheme)
                .host(it.toUrl().toURI().host)
                .port(it.port)
                .build()

            return@let chain.request().newBuilder()
                .url(newUrl)
                .build()
        }
        Timber.i("newRequest:${newRequest}")
        return chain.proceed(newRequest)
    }
}