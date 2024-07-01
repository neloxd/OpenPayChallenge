package com.jesusvilla.base.models

data class ProgressDialogModel(
    val message: String,
    val dialogOptions: ProgressDialogOptions = ProgressDialogOptions()
)

data class ProgressDialogOptions(
    val isCancellable: Boolean = false
)