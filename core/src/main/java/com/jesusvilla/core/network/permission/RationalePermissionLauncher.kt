package com.jesusvilla.core.network.permission

class RationalePermissionLauncher(
    private val canceled: () -> Unit,
    private val denied: () -> Unit,
    private val accepted: () -> Unit,
) {

    fun cancel() {
        canceled()
    }

    fun deny() {
        denied()
    }

    fun accept() {
        accepted()
    }
}