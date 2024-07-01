package com.jesusvilla.core.network.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response

class BaseResponse<T>(
    @SerializedName("page")
    var page: Int? = 1,
    @SerializedName("results")
    var payload: T? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null,
    @SerializedName("code")
    var code: String? = "200",
    @SerializedName("message")
    var message: String? = null) {

    constructor(throwableError: Throwable) : this() {
        code = "500"
        payload = null
        message = throwableError.message
    }

    constructor(response: Response<BaseResponse<T>>) : this() {
        code = response.code().toString()
        if (response.isSuccessful) {
            payload = response.body()?.payload
            message = null
        } else {
            payload = null
            message = response.errorBody()?.string() ?: response.message()
        }
    }

    fun isSuccessful(): Boolean = code?.toInt() in 200..299

}
