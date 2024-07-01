package com.jesusvilla.core.network.permission

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class FragmentMultiplePermissionsLauncher(
    private val fragment: Fragment,
    private val contract: Permissions,
) : MultiplePermissionsLauncher(contract) {

    private val activityResultCallback =
        ActivityResultCallback<Map<String, Boolean>> { handleResult(it) }

    private val activityResultLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        activityResultCallback,
    )

    override fun checkPermission(permission: String) =
        fragment.requireContext().hasPermission(permission)

    override fun checkAllPermissions() =
        fragment.requireContext().hasAllPermissions(*contract.rawPermissions.toTypedArray())

    override fun shouldShowRequestPermissionRationales() =
        contract.listPermissions.filter { fragment.shouldShowRequestPermissionRationale(it) }.toSet()

    override fun launch() {
        activityResultLauncher.launch(contract.listPermissions.toTypedArray())
    }
}