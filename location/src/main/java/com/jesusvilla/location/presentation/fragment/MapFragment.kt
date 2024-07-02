package com.jesusvilla.location.presentation.fragment

import androidx.fragment.app.FragmentTransaction
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jesusvilla.base.constants.Constants.COARSE_LOCATION_PERMISSION
import com.jesusvilla.base.constants.Constants.FINE_LOCATION_PERMISSION
import com.jesusvilla.base.lifecycle.Event
import com.jesusvilla.base.ui.BaseFragment
import com.jesusvilla.base.utils.UiText
import com.jesusvilla.base.utils.requestMultiplePermissionsLauncher
import com.jesusvilla.core.network.permission.allOf
import com.jesusvilla.location.R
import com.jesusvilla.location.databinding.FragmentLocationBinding
import com.jesusvilla.location.presentation.geocode.GeoLocationServices
import com.jesusvilla.location.viewModel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment: BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {

    private val viewModel: MapViewModel by hiltNavGraphViewModels(R.id.location_graph)
    override fun getBaseViewModel() = viewModel

    private val locationPermissionLauncher = requestMultiplePermissionsLauncher(
        allOf(FINE_LOCATION_PERMISSION, COARSE_LOCATION_PERMISSION)
    )

    companion object {
        const val MAP = "map_fragment"
        const val DEFAULT_ZOOM = 18F
        const val DEFAULT_ANIMATE = 200
    }

    private var mMap: GoogleMap? = null

    override fun setupUI() {
        super.setupUI()
        invokePermission()
        initMap()
    }

    override fun setUpObservers() {
        super.setUpObservers()
        viewModel.getLocations().observe(viewLifecycleOwner, ::addItems)
    }

    private fun addItems(list: List<MarkerOptions>) {
        list.forEach {
            mMap?.addMarker(it)
        }
    }

    private fun invokePermission() {
        locationPermissionLauncher.launch(
            onSuccess = {
            },
            onCancel = { permissions, rationalePermissionLauncher -> showNotificationToast(Event(UiText.DynamicString("Permiso de ubicación cancelado")))},
            onDenied = { permissions, neverAskAgain -> showNotificationToast(Event(UiText.DynamicString("Permiso de ubicación denegado")))}
        )
    }

    private fun initMap() {
        val mapFragment = SupportMapFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(
            R.id.base_fragment,
            mapFragment,
            MAP
        )
        transaction.commit()
        mapFragment.getMapAsync {
            mMap = it
            mMap!!.isMyLocationEnabled = true;
            mMap!!.uiSettings.isMyLocationButtonEnabled = true;
            mMap!!.uiSettings.isCompassEnabled = true
            mMap!!.uiSettings.isMapToolbarEnabled = false
            initCurrentLocation()
            viewModel.listener()
        }
    }

    private fun initCurrentLocation() {
        GeoLocationServices.setupFusedLocationProviderClient(LocationServices.getFusedLocationProviderClient(requireActivity()))
        GeoLocationServices.getLocation(
            context = requireActivity(),
            hasNotPermission = { invokePermission() },
            callbackLocation = { location, time ->
                val latLng = LatLng(location.latitude, location.longitude)
                //viewModel.saveCurrentLocation(latLng, time)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM)
                mMap?.animateCamera(cameraUpdate, DEFAULT_ANIMATE, object: CancelableCallback {
                    override fun onCancel() {

                    }

                    override fun onFinish() {

                    }
                })
            })
    }
}