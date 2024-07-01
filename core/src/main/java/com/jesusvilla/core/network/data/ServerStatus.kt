package com.jesusvilla.core.network.data

import java.net.HttpURLConnection

/**
 * Created by Jesus Villa on 30/01/24.
 *
 * Lima, Peru.
 */
enum class ServerStatus(var value: String) {
    SERVER_UNAVAILABLE(HttpURLConnection.HTTP_UNAVAILABLE.toString()),
    SERVER_ERROR(HttpURLConnection.HTTP_INTERNAL_ERROR.toString()), // 500
    CLIENT_TIMEOUT(HttpURLConnection.HTTP_CLIENT_TIMEOUT.toString()), // 408
    CREATED(HttpURLConnection.HTTP_CREATED.toString()), // 201
    SUCCESS(HttpURLConnection.HTTP_OK.toString()), // 200
    SUCCESS_NO_CONTENT(HttpURLConnection.HTTP_NO_CONTENT.toString()), // 204
    CONFLICT(HttpURLConnection.HTTP_CONFLICT.toString()), // 409
    UNAUTHORIZED(HttpURLConnection.HTTP_UNAUTHORIZED.toString()), // 401
    FORBIDDEN(HttpURLConnection.HTTP_FORBIDDEN.toString()), //403
    BAD_REQUEST(HttpURLConnection.HTTP_BAD_REQUEST.toString()), // 400
    NOT_FOUND(HttpURLConnection.HTTP_NOT_FOUND.toString()) // 404
}