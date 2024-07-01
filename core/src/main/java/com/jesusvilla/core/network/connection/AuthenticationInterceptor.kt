package com.jesusvilla.core.network.connection

import com.jesusvilla.core.network.connection.NetworkConfiguration.AUTHENTICATION_HEADER
import com.jesusvilla.core.network.data.ServerStatus
import com.jesusvilla.core.network.data.Session
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor() : Interceptor, Authenticator {

    private val hasSession: Boolean
        get() = runBlocking {
            Session.hasSession()
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if(hasSession) {
            val newRequest = chain.request()
                .newBuilder()
            newRequest.addHeader(AUTHENTICATION_HEADER, Session.session!!)
            val newBuilder = newRequest.build()
            val newResponse = chain.proceed(newBuilder)
            if(isUnauthorizedCode(newResponse.code)){
                Session.invalidate()
            }
            newResponse
        } else {
            val newResponse = chain.proceed(chain.request())
            if(isUnauthorizedCode(newResponse.code)){
                Session.invalidate()
            }
            newResponse
        }
    }

    override fun authenticate(route: Route?, response: Response): Request {
        if(isUnauthorizedCode(response.code)) {
            Session.invalidate()
            return response.request
        }

        return response.request.newBuilder()
            .removeHeader(AUTHENTICATION_HEADER)
            .build()
    }

    private fun isUnauthorizedCode(code: Int): Boolean {
        return when(code) {
            ServerStatus.UNAUTHORIZED.value.toInt(),
            ServerStatus.FORBIDDEN.value.toInt() -> true
            else -> false
        }
    }

}
