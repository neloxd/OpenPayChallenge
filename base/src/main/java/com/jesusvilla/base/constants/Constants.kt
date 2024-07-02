package com.jesusvilla.base.constants

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {
    @RequiresApi(Build.VERSION_CODES.P)
    const val BIOMETRIC_PERMISSION = Manifest.permission.USE_BIOMETRIC
    const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    const val WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val IMAGE_PERMISSION = Manifest.permission.READ_MEDIA_IMAGES
    const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
}