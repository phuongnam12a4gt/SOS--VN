package com.example.sos__vn.screen.Register

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sos__vn.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.fragment_register_location.*
import java.lang.Exception

class RegisterLocationFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener {

    private var map: GoogleMap? = null
    private var placesClient: PlacesClient? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationPermissionGranted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapFragment = childFragmentManager
            ?.findFragmentById(R.id.mapViewDragRetrieveLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        searchViewFindAddress.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    var location = searchViewFindAddress.query.toString()
                    var listAdress = mutableListOf<Address>()
                    if (location != null) {
                        var geo = Geocoder(activity)
                        try {
                            listAdress = geo.getFromLocationName(location, 1)
                            var address = listAdress.get(0)
                            var latlng = LatLng(address.latitude, address.longitude)
                            map?.addMarker(MarkerOptions().position(latlng).title("${location}"))
                            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 20f))

                        } catch (e: Exception) {
                            Log.i("TAG", e.toString())
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        getLocationPermission()
        p0?.let { getDeviceLocation(p0) }
    }

    private fun initData() {
        activity?.applicationContext?.let {
            Places.initialize(
                it,
                getString(R.string.google_maps_key)
            )
        }
        placesClient = activity?.let { Places.createClient(it) }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    private fun getLocationPermission() {
        if (activity?.applicationContext?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun getDeviceLocation(map: GoogleMap) {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient?.lastLocation
                locationResult?.let { task ->
                    task.addOnCompleteListener {
                        it?.let {
                            if (it.isSuccessful) {
                                var location = it.result
                                var currentPostion = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                                map.addMarker(
                                    MarkerOptions()
                                        .position(currentPostion)
                                        .draggable(true)
                                        .title("Postion")
                                        .snippet("Select postion services")
                                )
                                map?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentPostion, DEFAULT_ZOOM.toFloat()
                                    )
                                )
                                map.setOnMarkerDragListener(this)
                            }
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.i("TAG", e.toString())
        }
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        Log.i("TAG", p0?.position.toString())
    }

    override fun onMarkerDragStart(p0: Marker?) {
        Log.i("TAG", "DragStart")
    }

    override fun onMarkerDrag(p0: Marker?) {
        Log.i("TAG", "DragEnd")
    }

    companion object {

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 20

        fun newInstance() = RegisterLocationFragment()
    }
}
