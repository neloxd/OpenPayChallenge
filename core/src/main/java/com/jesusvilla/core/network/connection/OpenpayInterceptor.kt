package com.jesusvilla.core.network.connection

import com.jesusvilla.core.network.connection.NetworkConfiguration.AUTHENTICATION_HEADER
import com.jesusvilla.core.network.connection.NetworkConfiguration.CONTENT_TYPE_HEADER
import com.jesusvilla.core.network.connection.NetworkConfiguration.X_COMPANY_UUID_HEADER
import com.jesusvilla.core.network.data.Session
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class OpenpayInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header(AUTHENTICATION_HEADER, Session.session.orEmpty())
            .header(CONTENT_TYPE_HEADER, NetworkConfiguration.APPLICATION_JSON)
            .method(original.method, original.body)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
