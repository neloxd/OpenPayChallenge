package com.jesusvilla.base.navigation

import androidx.annotation.IdRes

interface NavigationHandler {
    fun navigate(@IdRes dest: Int)
}
