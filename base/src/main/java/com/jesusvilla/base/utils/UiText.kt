package com.jesusvilla.base.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(
        val value: String
    ) : UiText()

    data class StringResource(
        @StringRes val id: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(
                id, *args.toTypedArray()
            )
        }
    }
}