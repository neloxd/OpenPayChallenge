package com.jesusvilla.base.utils

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * Created by Jesús Villa on 22/12/23
 */
fun NavController.safeNavigate(actionId: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
    currentDestination?.getAction(actionId)?.run { navigate(actionId, bundle, navOptions) }
}
