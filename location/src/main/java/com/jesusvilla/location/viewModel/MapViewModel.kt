package com.jesusvilla.location.viewModel

import android.location.Location
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.jesusvilla.base.models.BaseUI
import com.jesusvilla.base.viewModel.BaseViewModel
import com.jesusvilla.database.constants.dataBaseConfig
import com.jesusvilla.location.data.model.MessageLocation
import com.jesusvilla.location.di.FirebaseDatabaseInstanceQualifier
import com.jesusvilla.location.utils.buildMarker
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    @FirebaseDatabaseInstanceQualifier private val database: DatabaseReference
) : BaseViewModel() {

    private val locations = MutableLiveData<List<MarkerOptions>>()

    companion object {
        const val LOCATIONS = "LOCATIONS"
    }

    @CheckResult
    fun getLocations(): LiveData<List<MarkerOptions>> = locations

    fun saveCurrentLocation(latLng: LatLng, time: Long) {
        /*locations.value?.filter { Location.distanceBetween(
            latLng.latitude,
            latLng.longitude,
            it.position.latitude,
            it.position.longitude,
            []) }*/
        val message = MessageLocation(time, latLng.latitude, latLng.longitude)
        database.child(LOCATIONS).push().setValue(message)
    }

    fun listener() {
        val messagesReference = database.child(LOCATIONS)
        messagesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Timber.i("onDataChange -> dataSnapshot:${dataSnapshot.children}")
                val messages = dataSnapshot.children.mapNotNull { it.getValue(MessageLocation::class.java) }
                // Update the UI with the new list of messages.
                locations.postValue(messages.map { LatLng(it.latitude, it.longitude).buildMarker(it.currentDate.toString()) })
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here.
                Timber.e("onCancelled -> ${databaseError.toException()}")
            }
        })
    }
}