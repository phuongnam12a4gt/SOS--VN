package com.example.sos__vn.screen.Home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.sos__vn.R
import com.example.sos__vn.model.Location
import com.example.sos__vn.model.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class SearchPlacesFragment : Fragment(),
    OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var placesClient: PlacesClient? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationPermissionGranted: Boolean = false
    private var listUser = mutableListOf<User>()
    private var currentPosition = Location()
    private val bankinh: Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_places, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapFragment = childFragmentManager
            ?.findFragmentById(R.id.fragmentSearchPlaces) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            map = p0
            getLocationPermission()
            getDeviceLocation(p0)
        }
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
                    SearchPlacesFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                )
            }
        }
    }

    private fun initData() {
        listUser.add(
            User(
                1, "Cua hang so 1", 12, "Lac Long Quan", "0364236993", "", "https://", 1,
                Location(16.0785535138192, 108.15026432275772)
            )
        )
        listUser.add(
            User(
                1, "Cua hang so 2", 12, "Lac Long Quan", "0364236993", "", "https://", 2,
                Location(16.07731705569745, 108.15191421657799)
            )
        )
        listUser.add(
            User(
                1, "Cua hang so 3", 12, "Lac Long Quan", "0364236993", "", "https://", 2,
                Location(16.08063466358783, 108.153489343822)
            )
        )
        activity?.applicationContext?.let {
            Places.initialize(
                it,
                getString(R.string.google_maps_key)
            )
        }
        placesClient = activity?.let { Places.createClient(it) }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    private fun getDeviceLocation(map: GoogleMap) {
        try {
            map.clear()
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient?.lastLocation
                locationResult?.let { task ->
                    task.addOnCompleteListener {
                        it?.let {
                            if (it.isSuccessful) {
                                var location = it.result
                                this.currentPosition =
                                    Location(location.latitude, location.longitude)
                                Log.i("TAG", this.currentPosition.lat.toString())
                                var currentPostion1 = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                                map.addMarker(
                                    MarkerOptions()
                                        .position(currentPostion1)
                                        .title("Current Position")
                                        .icon(
                                            BitmapDescriptorFactory.defaultMarker(
                                                BitmapDescriptorFactory.HUE_GREEN
                                            )
                                        )
                                )
                                map?.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentPostion1,
                                        15f
                                    )
                                )
                                showNeighborPosition(map)
                            }
                        }
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.i("TAG", e.toString())
        }
    }

    private fun showNeighborPosition(map: GoogleMap) {
        currentPosition?.let {
            for (i in 0..listUser.size - 1) {
                if ((listUser.get(i).location.lat - it.lat) * (listUser.get(i).location.lat - it.lat) + (listUser.get(
                        i
                    ).location.lng - it.lng) * (listUser.get(i).location.lng - it.lng)
                    <= (bankinh * bankinh)
                ) {
                    map.addMarker(
                        MarkerOptions()
                            .position(
                                LatLng(listUser.get(i).location.lat, listUser.get(i).location.lng)
                            ).title("${listUser.get(i).mname}")
                    )
                }
            }
        }
//     var current = LatLng(16.08063466358783,108.153489343822)
//        map.addMarker(
//            MarkerOptions()
//                .position(current)
//                .draggable(true)
//                .title("Postion")
//                .snippet("Choose a location here")
//        )
    }

    companion object {

        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        fun newInstances() = SearchPlacesFragment()
    }
}
