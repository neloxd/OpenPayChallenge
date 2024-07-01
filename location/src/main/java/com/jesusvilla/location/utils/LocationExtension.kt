package com.jesusvilla.location.utils

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun LatLng.buildMarker(title: String): MarkerOptions {
    return MarkerOptions().position(this).title(title)
}

fun Long.formatDateExtend(): String {
    val sdfExtend = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
    return sdfExtend.format(Date(this)).replace("\\.".toRegex(), "")
}