package com.jesusvilla.base.utils

import androidx.fragment.app.Fragment
import com.jesusvilla.core.network.permission.FragmentMultiplePermissionsLauncher
import com.jesusvilla.core.network.permission.MultiplePermissionsLauncher
import com.jesusvilla.core.network.permission.Permissions

fun Fragment.requestMultiplePermissionsLauncher(multiplePermissionsContract: Permissions): MultiplePermissionsLauncher =
    FragmentMultiplePermissionsLauncher(this, multiplePermissionsContract)