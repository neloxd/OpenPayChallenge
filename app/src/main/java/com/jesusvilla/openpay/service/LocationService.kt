package com.jesusvilla.openpay.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.location.Location
import android.location.LocationListener
import android.location.LocationRequest
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.FirebaseDatabase
import com.jesusvilla.location.data.model.MessageLocation
import com.jesusvilla.location.viewModel.MapViewModel.Companion.LOCATIONS
import com.jesusvilla.openpay.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class LocationService @Inject constructor() : Service(), LocationListener {
    var isEnded: Boolean = false
    protected var mGoogleApiClient: GoogleApiClient? = null
    protected var mLocationRequest: LocationRequest? = null


    private var latitude = 0.0
    private var longitude = 0.0
    private val results1 = FloatArray(1)
    private var distanceInMeters = 0f
    private var mHandler: Handler? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate() {
        super.onCreate()
        Timber.i("onCreate")
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "location_notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?)!!.createNotificationChannel(
                channel
            )
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setOngoing(true)
            .setContentTitle("OpenPay")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Obteniendo localización").build()

        //startForeground(NOTIFICATION_ID, notification)

        Toast.makeText(applicationContext, "ONCREATE SERVICE", Toast.LENGTH_LONG).show()
        if (Build.VERSION.SDK_INT >= 34) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
        }else {
            startForeground(
                NOTIFICATION_ID,
                notification)
        }
        //startForeground(101, notification);
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isEnded = false
        Timber.i("ONSTART COMMAND WAS HIT")
        mHandler = Looper.myLooper()?.let { Handler(it) }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()
        return START_STICKY
    }

    override fun onLocationChanged(location: Location) {
        //if (location.accuracy < HORIZONTAL_ACCURACY_IN_METERS) updateUI(location)
        updateUI(location)
    }

    /**
     * Updates the latitude, the longitude, and the last location time in the UI.
     */
    private fun updateUI(mCurrentLocation: Location) {
        Timber.i("updateUI")
        mHandler?.post {
            /*GET DEVICE CURRENT BATTERY LEVEL*/


            /*  CALCULATE DISTANCE BETWEEN LAT LONG INTERVALS*/
            if (latitude != 0.0 && longitude != 0.0) {
                Location.distanceBetween(
                    latitude,
                    longitude,
                    mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude(),
                    results1
                )
                distanceInMeters = results1[0]
            }


            latitude = mCurrentLocation.getLatitude()
            longitude = mCurrentLocation.getLongitude()

            val message = MessageLocation(Calendar.getInstance().timeInMillis, latitude, longitude)
            FirebaseDatabase.getInstance().reference.child(LOCATIONS).push().setValue(message)
            stopSelf()
        }
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient?.let {
                it.lastLocation.addOnSuccessListener { location ->
                    run {
                        Timber.i("addOnSuccessListener")
                        updateUI(location)
                    }
                }.addOnFailureListener {
                    Timber.i("addOnFailureListener")
                }
            }
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    fun stopLocationUpdates() {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false
        }
    }

    override fun onDestroy() {
        mHandler?.removeCallbacksAndMessages(null)

        stopLocationUpdates()

        super.onDestroy()
    }


    companion object {
        const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = (1000 * 30 //30 secs
                ).toLong()
        const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
        const val TAG: String = "LocationUpdateService"
        const val HORIZONTAL_ACCURACY_IN_METERS: Int = 100

        const val CHANNEL_ID = "FOREGROUND_CHANNEL"

        /**
         * The identifier for the notification displayed for the foreground service.
         */
        const val NOTIFICATION_ID = 12345678

        var mRequestingLocationUpdates: Boolean = false
    }
}