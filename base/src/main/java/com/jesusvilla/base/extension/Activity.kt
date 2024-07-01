package com.jesusvilla.base.extension

import android.app.Activity
import android.widget.Toast

fun Activity.makeToastShort(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.makeToastLong(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}