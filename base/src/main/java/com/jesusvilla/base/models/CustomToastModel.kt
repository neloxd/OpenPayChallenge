package com.jesusvilla.base.models

class CustomToastModel(
    val text: String,
    val type: CustomToastType = CustomToastType.ERROR
)

enum class CustomToastType {
    ERROR,
    SUCCESS,
    WARNING
}
