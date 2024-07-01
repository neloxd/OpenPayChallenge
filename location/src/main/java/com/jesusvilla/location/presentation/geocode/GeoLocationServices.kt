package com.jesusvilla.location.presentation.geocode

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Calendar


object GeoLocationServices {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    fun setupFusedLocationProviderClient(client: FusedLocationProviderClient) {
        if(!this::fusedLocationProviderClient.isInitialized)
            this.fusedLocationProviderClient = client
    }

    fun getLocation(context: Context, hasNotPermission: () -> Unit, callbackLocation:(location: Location, time: Long) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    run {
                        callbackLocation(location, Calendar.getInstance().timeInMillis)
                    }
                }.addOnFailureListener { }
        } else {
            // Request location permission if not granted
            hasNotPermission();
        }
    }

    suspend fun getLocation() {

    }
}