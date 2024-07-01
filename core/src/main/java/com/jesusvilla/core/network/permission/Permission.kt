package com.jesusvilla.core.network.permission

internal fun String.isUselessPermission() = endsWith(USE_LESS_SUFFIX)
internal fun String.normalizePermission() = removeSuffix(USE_LESS_SUFFIX)
private const val USE_LESS_SUFFIX = ".useLess"