package com.jesusvilla.core.network.data

/**
 * Created by Jesús Villa on 10/03/24
 */
class Resource<T> private constructor(
    val status: Status,
    val code: String?,
    var data: T?,
    val message: String?,
    val codeHttp: Int = 0,
    val isNetworkRelated: Boolean = false,
) {
    companion object {

        fun <T> success(data: T?, msg: String? = null, code: String? = null, codeHttp: Int = 200): Resource<T> {
            return Resource(
                Status.SUCCESS,
                code,
                data,
                msg,
                codeHttp
            )
        }

        fun <T> error(msg: String, code: String? = null, codeHttp: Int = 999, isNetworkRelated: Boolean = false): Resource<T> {
            return Resource(
                Status.ERROR,
                code,
                null,
                msg,
                codeHttp,
                isNetworkRelated
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                null,
                data,
                null,
            )
        }
    }
}