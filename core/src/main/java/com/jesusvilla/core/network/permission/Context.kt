package com.jesusvilla.core.network.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED || permission.isUselessPermission()

fun Context.hasAllPermissions(vararg permissions: String) = permissions.map { hasPermission(it) }.none { !it }

fun Context.checkAnyPermissions(vararg permissions: String) = permissions.map { hasPermission(it) }.any { it }