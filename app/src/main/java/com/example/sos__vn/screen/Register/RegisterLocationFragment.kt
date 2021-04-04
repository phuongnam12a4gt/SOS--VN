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
import com.example.sos__vn.model.Location
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
    private val listLocationSelect = mutableListOf<Location>()

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
                    map?.clear()
                    var location = searchViewFindAddress.query.toString()
                    var listAdress = mutableListOf<Address>()
                    if (location != null) {
                        var geo = Geocoder(activity)
                        try {
                            listAdress = geo.getFromLocationName(location, 1)
                            var address = listAdress.get(0)
                            listLocationSelect.clear()
                            listLocationSelect.add(Location(address.latitude, address.longitude))
                            var latlng = LatLng(address.latitude, address.longitude)
                            map?.addMarker(
                                MarkerOptions().position(latlng).title("${location}")
                                    .draggable(true)
                            )
                            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
                            map?.setOnMarkerDragListener(this@RegisterLocationFragment)
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
        buttonCurrentLocation.setOnClickListener {
            getDeviceLocation(map!!)
        }
        buttonSavePostion.setOnClickListener {
            Log.i("TAG", listLocationSelect.get(0).lat.toString())
        }
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
            map.clear()
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
                                listLocationSelect.clear()
                                listLocationSelect.add(
                                    Location(
                                        location.latitude,
                                        location.latitude
                                    )
                                )
                                map.addMarker(
                                    MarkerOptions()
                                        .position(currentPostion)
                                        .draggable(true)
                                        .title("Postion")
                                        .snippet("Choose a location here")
                                )
                                map?.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        currentPostion,
                                        15f
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
        Log.i("TAG", p0?.position?.latitude.toString() + " " + p0?.position?.longitude.toString())
        listLocationSelect.clear()
        p0?.let {
            listLocationSelect.add(Location(p0.position.latitude, p0.position.longitude))
        }
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
