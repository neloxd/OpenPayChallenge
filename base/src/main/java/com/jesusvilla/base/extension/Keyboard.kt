package com.jesusvilla.base.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getVersionName(): String {
    return packageManager.getPackageInfo(this.packageName, 0).versionName
}

fun Activity.forceHideKeyboard() {
    var view = this.currentFocus
    if(view == null)
        view = View(this)
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}