package com.jesusvilla.core.network.data

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

/**
 * Determines errors that come from the server, here you can add more types such as shake
 */
fun Exception.isNetworkRelated(): Boolean {
    return this is IOException || this is HttpException || this is JsonSyntaxException
}