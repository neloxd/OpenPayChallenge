package com.jesusvilla.core.network.permission

abstract class MultiplePermissionsLauncher(
    private val permissions: Permissions,
) {
    private var onDeniedCallback: ((permissions: Set<String>, neverAskAgain: Boolean) -> Unit)? =
        null
    private var onSuccessCallback: (() -> Unit)? = null
    private var rationaleWasRequiredBeforePermissionRequest: Boolean = false
    private var rationalePermissionLauncher: RationalePermissionLauncher? = null

    fun launch(
        onSuccess: () -> Unit,
        onCancel: ((permissions: Set<String>, RationalePermissionLauncher) -> Unit)? = null,
        onDenied: ((permissions: Set<String>, neverAskAgain: Boolean) -> Unit)? = null,
    ) {
        this.onDeniedCallback = onDenied
        this.onSuccessCallback = onSuccess

        val hasPermissions = when (permissions) {
            is Permissions.AllOf -> checkAllPermissions()
        }

        val rationales = shouldShowRequestPermissionRationales()
        rationaleWasRequiredBeforePermissionRequest = rationales.isNotEmpty()

        when {
            hasPermissions -> internalGranted()

            rationales.isNotEmpty() -> {
                rationalePermissionLauncher = RationalePermissionLauncher(
                    ::internalCancelled,
                    { internalDenied(rationales) },
                ) { launch() }
                onCancel?.invoke(rationales, rationalePermissionLauncher!!)
            }

            else -> launch()
        }
    }

    protected abstract fun checkPermission(permission: String): Boolean
    protected abstract fun checkAllPermissions(): Boolean
    protected abstract fun shouldShowRequestPermissionRationales(): Set<String>
    protected abstract fun launch()

    protected fun handleResult(map: Map<String, Boolean>) {
        when (permissions) {
            is Permissions.AllOf ->
                if (checkAllPermissions()) {
                    internalGranted()
                } else {
                    internalDenied(map.filter { !it.value }.keys.toSet())
                }
        }
    }

    private fun internalCancelled() {
        resetCallbacks()
    }

    private fun internalDenied(permissions: Set<String>) {
        onDeniedCallback?.invoke(
            permissions,
            !rationaleWasRequiredBeforePermissionRequest && shouldShowRequestPermissionRationales().isEmpty(),
        )
        resetCallbacks()
    }

    private fun internalGranted() {
        onSuccessCallback?.invoke()
        resetCallbacks()
    }

    private fun resetCallbacks() {
        onDeniedCallback = null
        onSuccessCallback = null
        rationalePermissionLauncher = null
    }
}

sealed class Permissions(val rawPermissions: Set<String>) {

    val listPermissions = rawPermissions.map { it.normalizePermission() }.toSet()

    class AllOf(permissions: Collection<String>) : Permissions(permissions.toSet())
}

fun allOf(vararg permissions: String): Permissions = Permissions.AllOf(permissions.toSet())
